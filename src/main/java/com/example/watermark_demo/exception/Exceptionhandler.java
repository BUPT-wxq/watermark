package com.example.watermark_demo.exception;


import com.example.watermark_demo.Response.BasicResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import  org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class Exceptionhandler {

    @ExceptionHandler(Exception.class)
    public BasicResponse handler(Exception e){
        e.printStackTrace();
        //"uid或密码不符合长度规则"
        return BasicResponse.error(StringUtils.hasLength(e.getMessage())? e.getMessage() : "操作失败");

    }
}
