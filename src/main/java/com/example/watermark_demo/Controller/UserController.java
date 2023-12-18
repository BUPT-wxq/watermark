package com.example.watermark_demo.Controller;

import com.example.watermark_demo.Response.BasicResponse;
import com.example.watermark_demo.Response.userLoginResponse;
import com.example.watermark_demo.Service.iml.UserRegister;
import com.example.watermark_demo.data.entity.User;
//import com.example.watermark_demo.data.entity.dao.UserRegisterRep;
import com.example.watermark_demo.data.entity.dao.UserRegisterRepository;
import com.example.watermark_demo.handler.RegisterHandler;
import com.example.watermark_demo.utils.JwtUtil;
import com.example.watermark_demo.utils.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Component
@Validated
@Slf4j
@CrossOrigin
public class UserController {


    @Autowired
    private UserRegister userRegister;
    @Autowired
    private RegisterHandler registerHandler;
    @Autowired
    private UserRegisterRepository userRegisterRepository;

    @PostMapping("/test111")
    public String test(@RequestBody User user) {
        System.out.println(user.getUid());
        return "ok";
    }


    @PostMapping("/register")
    //@Pattern(regexp="^\\S{5,16}$") String uid,@Pattern(regexp="^\\S{5,16}$") String password, String username, String phone, String email, String department
    public BasicResponse register(@RequestBody User user) {
        System.out.println("1" + user.getUid());
        //查询用户是否存在


        String status = registerHandler.user_register(user);
        if (status.equals("uid已存在")) return BasicResponse.error(status);
//            userRegister.register(uid,password,username,phone,email,department);
        User user2 = userRegister.findByUserId(user.getUid());
        System.out.println(user2);
        if (user2 != null) return BasicResponse.success("注册成功");


        return BasicResponse.error("uid已被占用");

    }
    @PostMapping("/login")
    public BasicResponse login(@RequestBody User user) {
        //查询
        String uid = user.getUid();
        String password = user.getPassword();
        User user_login = userRegister.findByUserId(uid);
        if (user_login == null) {
            return userLoginResponse.error("uid错误或不存在,请检查输入");
        }

        //登录验证密码
        //后续应该添加令牌认证功能
        if (Md5Util.getMD5String(password).equals(user_login.getPassword())) {
            Map<String,Object> claims = new HashMap<>();
            claims.put("uid",user_login.getUid());
            claims.put("password",user_login.getPassword());
            String token = JwtUtil.genToken(claims);
//            return BasicResponse.success("密码验证成功");
            return userLoginResponse.success("登录成功",user_login,token);
        }
        return  userLoginResponse.error("密码错误");


    }

}
