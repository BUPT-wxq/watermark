package com.example.watermark_demo.Service.iml;

import com.example.watermark_demo.data.entity.WmTemplate;
import com.example.watermark_demo.data.entity.db_info.DB_WmTemplate;

import java.util.List;

public interface TemplateCrud {

    //查询，返回模板对象
    List<DB_WmTemplate> GetAllRecords(String uid);
    //增加
//    void insert(WmTemplate template) ;

    void update(String targetFigerprint);

//    void findtemplatebytemplateId(int )



    //删除
    void delete(String uid,int templateId);



    void insert(String uid, String figerprint, String content, String fontColor, int fontSize, int frameSize, double alpha, int angle, String key);
}
