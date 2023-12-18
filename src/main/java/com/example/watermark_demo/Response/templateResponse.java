package com.example.watermark_demo.Response;


import com.example.watermark_demo.data.entity.WmTemplate;
import com.example.watermark_demo.data.entity.db_info.DB_WmTemplate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class templateResponse<T> {
    private   String statusCode;//状态码 成功200，失败400
    private   String statusContent;//状态信息
    private List<DB_WmTemplate> queryresult;
    public static <E> templateResponse<E> query_success(String statusContent, List<DB_WmTemplate> queryresult) {
//        InputStream file= fileResponse.class.getResourceAsStream(path);
        return new templateResponse<E>("200", statusContent, queryresult);
    }
    public static <E> templateResponse<E> query_defeat(String statusContent) {
//        InputStream file= fileResponse.class.getResourceAsStream(path);
        return new templateResponse<E>("400", statusContent,null);
    }

}
