package com.example.watermark_demo.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasicResponse<T> {
    private   String statusCode;//状态码 成功200，失败400
    private   String statusContent;//状态信息


    //对注册登录成功的状态响应
    public static <E> BasicResponse<E> success(String statusContent){
        return new BasicResponse<>("200", statusContent);
    }

    //对注册登录失败的状态响应
    public static <E> BasicResponse<E> error(String statusContent){

        return new BasicResponse<>("400", statusContent);
    }






}
