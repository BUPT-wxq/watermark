package com.example.watermark_demo.Response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageResponse<T> {
    private  String uri;



    public static <E> ImageResponse<E> url(String filename){

        return new ImageResponse<> (filename);
    }

}
