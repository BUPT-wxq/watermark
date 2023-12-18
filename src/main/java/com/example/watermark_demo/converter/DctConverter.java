package com.example.watermark_demo.converter;


import com.example.watermark_demo.utils.BlindWmUtils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;

import static org.opencv.core.Core.*;
import static org.opencv.core.CvType.CV_32F;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.imgproc.Imgproc.*;

public class DctConverter implements Converter {
    @Override
    public Mat start(Mat src) {
        if ((src.cols() & 1) != 0) {
            copyMakeBorder(src, src, 0, 0, 0, 1, BORDER_CONSTANT, Scalar.all(0));
        }
        if ((src.rows() & 1) != 0) {
            copyMakeBorder(src, src, 0, 1, 0, 0, BORDER_CONSTANT, Scalar.all(0));
        }
        src.convertTo(src, CV_32F);
        dct(src, src);
        return src;
    }

    @Override
    public void inverse(Mat com) {
        idct(com, com);
    }

    @Override
    public void addTextWatermark(Mat com, String watermark) {
        putText(com, watermark,
                new Point(com.cols() >> 1, com.rows() >> 1),
                FONT_HERSHEY_COMPLEX, 2.0,
                new Scalar(25, 25, 25, 0), 2, 8, false);
        System.out.println("addTextWatermark");
    }

    @Override
    public void addImageWatermark(Mat com, Mat watermark) {
        Mat mask = new Mat();
        inRange(watermark, new Scalar(0, 0, 0, 0), new Scalar(0, 0, 0, 0), mask);
        Mat i2 = new Mat(watermark.size(), watermark.type(), new Scalar(25, 25, 25, 0));
        i2.copyTo(watermark, mask);
        watermark.convertTo(watermark, CV_32F);
        while(watermark.cols()>com.cols() || watermark.rows()>com.rows()){
            resize(watermark, watermark, new Size(0.5*watermark.cols(),0.5*watermark.rows()));
        }
        int row = (com.rows() - watermark.rows()) >> 1;
        int col = (com.cols() - watermark.cols()) >> 1;
        copyMakeBorder(watermark, watermark, row, row, col, col, BORDER_CONSTANT, Scalar.all(0));
        BlindWmUtils.fixSize(watermark, com);
        addWeighted(watermark, 0.05, com, 1, 0.0, com);
        System.out.println("addImageWatermark");
    }

    @Override
    public Mat showWatermark(Mat src) {
        src.convertTo(src, COLOR_RGB2HSV);
        inRange(src, new Scalar(0, 0, 0, 0), new Scalar(25, 25, 25, 0), src);
        normalize(src, src, 0, 255, NORM_MINMAX, CV_8UC1);
        return src;
    }
}
