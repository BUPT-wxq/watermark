package com.example.watermark_demo.dencoder;


import com.example.watermark_demo.converter.Converter;
import com.example.watermark_demo.utils.BlindWmUtils;
import org.opencv.core.Mat;

public class TextEncoder extends Encoder {
    public TextEncoder(Converter converter) {
        super(converter);
    }

    @Override
    public void addWatermark(Mat com, String watermark) {
        if (BlindWmUtils.isAscii(watermark)) {
            this.converter.addTextWatermark(com, watermark);
        } else {
            this.converter.addImageWatermark(com, BlindWmUtils.drawNonAscii(watermark));
        }
//        this.converter.addTextWatermark(com, watermark);
    }
}
