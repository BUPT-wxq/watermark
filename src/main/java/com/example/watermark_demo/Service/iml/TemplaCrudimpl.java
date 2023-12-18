package com.example.watermark_demo.Service.iml;


import com.example.watermark_demo.Mapper.TemplateMapper;
import com.example.watermark_demo.data.entity.WmTemplate;
import com.example.watermark_demo.data.entity.db_info.DB_WmTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplaCrudimpl implements TemplateCrud {

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public List<DB_WmTemplate> GetAllRecords(String uid) {
        return  templateMapper.GetallRecords(uid);

    }

    @Override
    public void delete(String uid, int templateId) {
        templateMapper.delete(uid,templateId);

    }
    @Override
    public void update(String targetFigerprint){
        templateMapper.update(targetFigerprint);
    }

    @Override
    public void insert(String uid, String figerprint, String content, String fontColor, int fontSize, int frameSize, double alpha, int angle, String key) {
        templateMapper.insert(uid, figerprint, content,
                fontColor, fontSize, frameSize,
                alpha, angle, key);

    }


//    public void insert(WmTemplate template){
//        templateMapper.insert(template.getUid(),
//                template.getTargetFigerprint(),
//                template.getContent(),
//                template.getFontColor(),
//                template.getFontSize(),
//                template.getFrameSize(),
//                template.getAlpha(),
//                template.getAngle(),
//                template.getKey1());
//    }


}
