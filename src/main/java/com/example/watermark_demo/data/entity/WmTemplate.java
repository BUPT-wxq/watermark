package com.example.watermark_demo.data.entity;


import lombok.Data;

import java.util.List;

@Data
public class WmTemplate {

    private  String uid;
    private  int templateId;
    private  String name;
    private List targetFingerprint;
    private  String content;
    private  String fontColor;
    private  int fontSize;
    private  int frameSize;
    private  double alpha=0.5;
    private  int angle=30;
//    private  String key;



}
