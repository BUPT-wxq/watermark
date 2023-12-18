package com.example.watermark_demo.data.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data

public class returnFileBody {

    private String base64String;
    private String fileName;
    private String fileType;
}
