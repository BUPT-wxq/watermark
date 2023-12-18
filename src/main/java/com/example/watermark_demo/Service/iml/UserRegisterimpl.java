package com.example.watermark_demo.Service.iml;

import com.example.watermark_demo.Mapper.UserMapper;
import com.example.watermark_demo.data.entity.User;
import com.example.watermark_demo.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class UserRegisterimpl  implements UserRegister{
    @Autowired
    private UserMapper userMapper;

    public User findByUserId(String uid) {
        User u=userMapper.findByUserId(uid);
        return u;
    }

    @Override
    public void register(String uid, String password, String username, String phone, String email, String department) {
        String password_crypt = Md5Util.getMD5String(password);
        userMapper.add(uid, password_crypt, username, phone, email, department);

//    public BasicResponse register(String uid, String password, String username, String phone, String email, String department){
//        DB_User user=new DB_User();
//        user.set}

    }

}
