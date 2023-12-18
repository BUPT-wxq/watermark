package com.example.watermark_demo.Response;

import com.example.watermark_demo.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class userLoginResponse<T> extends BasicResponse{
    private   String statusCode;//状态码 成功200，失败400
    private   String statusContent;//状态信息
    private User user;
    private String token;




    public static <E> userLoginResponse<E> success(String statusContent,User user,String token){
        return new userLoginResponse<>("200",statusContent,user,token);
    }

//    public static <E> BasicResponse<E> error(String statusContent);

}
