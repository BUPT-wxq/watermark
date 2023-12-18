//package com.example.watermark_demo.Controller;
//
//import org.apache.commons.io.FileUtils;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//public class test {
//
//    @GetMapping("/getTemplateFile")
////    @ApiOperation("数据模板下载")
//    public ResponseEntity<byte[]> downFile(HttpServletRequest request) throws IOException {
//        File file = new File("C/AA");
//        String filename = getFilename(request);
//        //设置响应头
//        HttpHeaders headers = new HttpHeaders();
//        //通知浏览器以下载的方式打开文件
//        headers.setContentDispositionFormData("attachment", filename);
//        //定义以流的形式下载返回文件数据
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        //使用springmvc框架的ResponseEntity对象封装返回数据
//        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
//    }
//
//    /**
//     * 根据浏览器的不同进行编码设置
//     *
//     * @param request  请求对象
//     * @param filename 需要转码的文件名
//     * @return 返回编码后的文件名
//     * @throws IOException
//     */
//    public String getFilename(HttpServletRequest request) throws IOException {
//
//        //IE不同版本User-Agent中出现的关键词
//        String[] IEBrowserKeyWords = {"MSIE", "Trident", "Edge"};
//        //获取请求头代理信息
//        String userAgent = request.getHeader("User-Agent");
//        for (String keyWord : IEBrowserKeyWords) {
//            if (userAgent.contains(keyWord)) {
//                //IE内核浏览器，统一为utf-8编码显示
//                return URLEncoder.encode(filename, "UTF-8");
//            }
//        }
//        //火狐等其他浏览器统一为ISO-8859-1编码显示
//        return new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
//    }
//}
//
//
//
