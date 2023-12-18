package com.example.watermark_demo.handler;


import com.example.watermark_demo.data.entity.dao.UserRegisterRepository;
import com.example.watermark_demo.data.entity.db_info.DB_User;
import com.example.watermark_demo.data.entity.User;
import com.example.watermark_demo.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterHandler {


    @Autowired
    private UserRegisterRepository userRegisterRepository;

    public String user_register(User user) {
        System.out.println("执行插入用户" + user.getUid());

        DB_User user1 = userRegisterRepository.findFirstByUid(user.getUid());
        System.out.println(user1);
        if (user1 == null) {
            System.out.println("插入进行时");
            DB_User db_user = new DB_User();
            db_user.setUid(user.getUid());
            db_user.setPassword(Md5Util.getMD5String(user.getPassword()));
            db_user.setUsername(user.getUsername());
            db_user.setPhone(user.getPhone());
            db_user.setEmail(user.getEmail());
            db_user.setDepartment(user.getDepartment());
            userRegisterRepository.saveAndFlush(db_user);
            return "用户注册成功！！！";
        }
        return "uid已存在";


    }


}
