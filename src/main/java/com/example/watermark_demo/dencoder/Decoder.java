package com.example.watermark_demo.dencoder;



import com.example.watermark_demo.converter.Converter;
import com.example.watermark_demo.utils.BlindWmUtils;

import static org.opencv.core.CvType.CV_8U;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
public class Decoder {
    private Converter converter;

    public Decoder(Converter converter) {
        this.converter = converter;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void decode(String image, String output) {
        imwrite(output, this.converter.showWatermark(this.converter.start(BlindWmUtils.read(image, CV_8U))));
    }
}
