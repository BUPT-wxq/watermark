package com.example.watermark_demo.data.entity;


import lombok.Data;

import java.io.File;
import java.util.List;

@Data
public class FileWatermark {
//    private File file;
    private List targetFingerprint;
    private String content;
    private String fontColor;
    private int fontSize;
    private int frameSize;
    private double alpha;
    private int angle;
//    private String key;

}
