package com.example.watermark_demo.data.entity.db_info;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "wmtemplate")
public class DB_WmTemplate {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name="templateId")
    private int templateId;

    @Column(name = "uid",nullable = false)
    private  String uid;
    @Column(name="name",nullable = false)
    private  String name;

    @Column(name = "targetFingerprint",nullable = false)
    private  String targetFingerprint;

    @Column(name = "content")
    private  String content;

    @Column(name = "fontColor")
    private  String fontColor;

    @Column(name = "fontSize")
    private  int fontSize;

    @Column(name = "frameSize")
    private  int frameSize;

    @Column(name = "alpha")
    private  double alpha=0.5;

    @Column(name = "angle")
    private  int angle=30;

//    @Column(name = "key1",nullable = false)
//    private  String key1;


}
