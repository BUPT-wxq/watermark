package com.example.watermark_demo.Mapper;

import com.example.watermark_demo.data.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface UserMapper {
    //查询
    @Select("select * from user_info where uid=#{arg0}")
    User findByUserId(String uid);

    //添加用户
    @Insert("insert into user_info(uid,password,username,phone,email,department)"+
     " values(#{arg0},#{arg1},#{arg2},#{arg3},#{arg4},#{arg5})")
    void add(String uid, String password,String username,  String phone,  String email,  String department);
}
