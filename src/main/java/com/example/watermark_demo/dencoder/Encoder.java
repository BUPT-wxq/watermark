package com.example.watermark_demo.dencoder;


import com.example.watermark_demo.converter.Converter;
import com.example.watermark_demo.utils.BlindWmUtils;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.Core.merge;
import static org.opencv.core.Core.split;
import static org.opencv.core.CvType.CV_8S;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
public abstract class Encoder {
    Converter converter;

    Encoder(Converter converter) {
        this.converter = converter;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void encode(String image, String watermark, String output) {
        Mat src = BlindWmUtils.read(image, CV_8S);

        List<Mat> channel = new ArrayList<>(3);
        List<Mat> newChannel = new ArrayList<>(3);
        split(src, channel);

        for (int i = 0; i < 3; i++) {
            Mat com = this.converter.start(channel.get(i)).clone();
            this.addWatermark(com, watermark);
            this.converter.inverse(com);
            newChannel.add(i, com);
        }

        Mat res = new Mat();
        merge(newChannel, res);

        if (res.rows() != src.rows() || res.cols() != src.cols()) {
            res = new Mat(res, new Rect(0, 0, src.width(), src.height()));
        }

        imwrite(output, res);
    }

    public abstract void addWatermark(Mat com, String watermark);
}
