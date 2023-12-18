package com.example.watermark_demo.data.entity.dao;

import com.example.watermark_demo.data.entity.db_info.DB_WmTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateRepository extends JpaRepository<DB_WmTemplate, Integer> {
    List<DB_WmTemplate> findAllByUid(String uid);

    DB_WmTemplate findFirstByTemplateId(int templateId);

    DB_WmTemplate findFirstByTemplateIdAndUid(int templateId,String uid);

    DB_WmTemplate deleteDB_WmTemplateByTemplateIdAndUid(int templateId,String uid);
}
