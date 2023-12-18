package com.example.watermark_demo.handler;

import com.example.watermark_demo.data.entity.dao.TemplateRepository;
import com.example.watermark_demo.data.entity.dao.UserRegisterRepository;
import com.example.watermark_demo.data.entity.WmTemplate;
import com.example.watermark_demo.data.entity.db_info.DB_WmTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TemplateHandler {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private UserRegisterRepository userRegisterRepository;

    public String addTemplateHandler(WmTemplate template) {
        System.out.println("执行插入模板,uId：" + template.getUid());


        DB_WmTemplate wmTemplate = new DB_WmTemplate();
        List<DB_WmTemplate> wmTemplates = templateRepository.findAllByUid(template.getUid());
        if (wmTemplates.size() > 10) {
            // 如果记录数量超过10条
            return ("模板数量已超过10条！");
        }
//        wmTemplate=templateRepository.findFirstByTemplateIdAndUid(template.getTemplateId(),template.getUid());
//        if(wmTemplate!=null) return "";

        wmTemplate.setUid(template.getUid());
        wmTemplate.setTargetFingerprint(template.getTargetFingerprint().toString());
        wmTemplate.setName(template.getName());
        wmTemplate.setContent(template.getContent());
        wmTemplate.setFontColor(template.getFontColor());
        wmTemplate.setFontSize(template.getFontSize());
        wmTemplate.setFrameSize(template.getFrameSize());
        wmTemplate.setAlpha(template.getAlpha());
        wmTemplate.setAngle(template.getAngle());
//        wmTemplate.setKey1(template.getKey());
        templateRepository.saveAndFlush(wmTemplate);
        return "模板添加成功！";

//        wm
    }

    public List<DB_WmTemplate> findAllByUid(String uid) {
        return templateRepository.findAllByUid(uid);
    }

    public String update(WmTemplate wmTemplate) {

        DB_WmTemplate wmTemplate1;
        wmTemplate1 =templateRepository.findFirstByTemplateIdAndUid(wmTemplate.getTemplateId(),wmTemplate.getUid());
        if(wmTemplate1!=null){
            wmTemplate1.setUid(wmTemplate.getUid());
            wmTemplate1.setTargetFingerprint(wmTemplate.getTargetFingerprint().toString());
            wmTemplate1.setName(wmTemplate.getName());
            wmTemplate1.setContent(wmTemplate.getContent());
            wmTemplate1.setFontColor(wmTemplate.getFontColor());
            wmTemplate1.setFontSize(wmTemplate.getFontSize());
            wmTemplate1.setFrameSize(wmTemplate.getFrameSize());
            wmTemplate1.setAlpha(wmTemplate.getAlpha());
            wmTemplate1.setAngle(wmTemplate.getAngle());
//            wmTemplate1.setKey1(wmTemplate.getKey());
            templateRepository.saveAndFlush(wmTemplate1);
            return "模板修改成功!";
        }
        return "请检查模板Id和uid输入是否正确";


    }
}
