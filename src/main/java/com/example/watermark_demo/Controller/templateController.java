package com.example.watermark_demo.Controller;


import com.example.watermark_demo.Response.BasicResponse;
import com.example.watermark_demo.Response.templateResponse;
import com.example.watermark_demo.Service.iml.TemplateCrud;
import com.example.watermark_demo.data.entity.User;
import com.example.watermark_demo.data.entity.WmTemplate;
import com.example.watermark_demo.data.entity.db_info.DB_WmTemplate;
import com.example.watermark_demo.handler.TemplateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/watermark/template")

public class templateController {

    @Autowired
    private  TemplateCrud templateCrud;

    @Autowired
    private TemplateHandler templateHandler;

    @PostMapping("/test111")
    public String test( WmTemplate template) {
        System.out.println(template.getUid());
        return "ok";
    }


    @PostMapping("/insert")
    public BasicResponse insert(@RequestBody WmTemplate template){
//        String uid=template.getUid();
//        List Fingerprint=template.getTargetFingerprint();
//        System.out.println(Fingerprint);
        String status=templateHandler.addTemplateHandler(template);
        return BasicResponse.success(status);
    }



    @PostMapping("/query")
    public templateResponse query(@RequestBody User user){
//        List<DB_WmTemplate> temp=templateCrud.GetAllRecords(user.getUid());

        List<DB_WmTemplate> templates=templateHandler.findAllByUid(user.getUid());

//        WmTemplate templates_1= new WmTemplate();
        if(templates.isEmpty())
            return templateResponse.query_defeat("查询失败，请检查uid！");

        System.out.println(templates);
        return templateResponse.query_success("模板查询成功",templates);
    }
    @PostMapping("/modify")
    public BasicResponse modify(@RequestBody WmTemplate wmTemplate){
        String status=templateHandler.update(wmTemplate);
        return BasicResponse.success(status);
    }


    @PostMapping("/delete")
    public BasicResponse delete(@RequestBody WmTemplate template){
//        templateHandler.findAllByUid()
        templateCrud.delete(template.getUid(),template.getTemplateId());
        return BasicResponse.success("删除成功");
    }



}
