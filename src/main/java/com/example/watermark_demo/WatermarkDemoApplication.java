package com.example.watermark_demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication

//@EntityScan("com.example.watermark_demo.data.entity")
public class WatermarkDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WatermarkDemoApplication.class, args);
    }

}
