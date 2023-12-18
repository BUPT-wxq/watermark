package com.example.watermark_demo.Mapper;


import com.example.watermark_demo.data.entity.WmTemplate;
import com.example.watermark_demo.data.entity.db_info.DB_WmTemplate;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TemplateMapper {
    @Insert("insert into wmtemplate(uid,targetFigerprint,content,fontColor,fontSize,frameSize,alpha,angle,key1)"+
            " values(#{arg0},#{arg1},#{arg2},#{arg3},#{arg4},#{arg5},#{arg6},#{arg7},#{arg8})")
    void insert(String uid,
                String targetFigerprint,
                String content,
                String fontColor,
                int fontSize,
                int frameSize,
                double alpha,
                int angle,
                String key1);

    @Select("select * from wmtemplate where uid=#{arg0}")
    List<DB_WmTemplate> GetallRecords(String uid);

    @Delete("delete from wmtemplate where uid=#{arg0} and template_id=#{arg1}")
    void delete(String uid, int templateId);

    @Update("update wmtemplate set targetFingerprint=#{arg0} where uid=#{arg1}")
    void update(String targetFingerprint);
}
