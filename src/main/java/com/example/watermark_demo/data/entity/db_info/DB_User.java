package com.example.watermark_demo.data.entity.db_info;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_info")
public class DB_User {

    @Id
//    @GeneratedValue(
//            strategy = GenerationType.IDENTITY
//    )

    @Column(name = "uid",nullable = false)
    private String uid;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "username",nullable = false)
    private String username;

    @Column(name = "phone",nullable = false)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "department",nullable = false)
    private String department;


}
