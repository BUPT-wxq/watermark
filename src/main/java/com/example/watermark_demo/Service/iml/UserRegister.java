package com.example.watermark_demo.Service.iml;

import com.example.watermark_demo.data.entity.User;

public interface UserRegister {
    //查询
    User findByUserId(String uid);
    //注册
    void register(String uid, String password, String username, String phone, String email, String department);
}
