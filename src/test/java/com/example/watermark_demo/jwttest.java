package com.example.watermark_demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class jwttest {
    @Test
    public void Test() {
        Map<String, Object> claims=new HashMap<>();
        claims.put("uid",1);
        claims.put("password",12345);
        //generate Jwt
        String token=JWT.create()
                .withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*2))//添加过期时间
                .sign(Algorithm.HMAC256("12314"));//
        System.out.println(token);
    }
}
