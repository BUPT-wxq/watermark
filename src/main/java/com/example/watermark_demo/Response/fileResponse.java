package com.example.watermark_demo.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.InputStream;


@AllArgsConstructor
@NoArgsConstructor
@ResponseBody
public class fileResponse<T> {
    private   String statusCode;//状态码 成功200，失败400
    private   String statusContent;//状态信息

    private File file;

    public static <E> fileResponse<E> success_file(String statusContent,File file){
//        InputStream file= fileResponse.class.getResourceAsStream(path);
        return new fileResponse<E>("200",statusContent, file);
    }
}
