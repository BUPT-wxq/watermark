package com.example.watermark_demo.data.entity.dao;
import com.example.watermark_demo.data.entity.db_info.DB_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRegisterRepository extends JpaRepository<DB_User, Integer>{


    DB_User findFirstByUid(String uid);


}
