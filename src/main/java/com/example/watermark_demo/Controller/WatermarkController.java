package com.example.watermark_demo.Controller;


import com.example.watermark_demo.Response.BasicResponse;
import com.example.watermark_demo.Response.fileResponse;
import com.example.watermark_demo.Response.myHttpStatus;
import com.example.watermark_demo.data.entity.FileWatermark;
import com.example.watermark_demo.data.entity.Uri;
import com.example.watermark_demo.data.entity.WmTemplate;
import com.example.watermark_demo.data.entity.returnFileBody;
import com.example.watermark_demo.utils.AddWmToPicUtils;
import com.example.watermark_demo.utils.AddwmToPdfUtil;
import com.example.watermark_demo.utils.GenerateWmUtil;
import com.example.watermark_demo.utils.*;
import com.example.watermark_demo.utils.UploadUtils;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
//import java.awt.Image;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Objects;


import static com.example.watermark_demo.utils.filetomultipartfileutil.fileTOMultipartfile;


@CrossOrigin(value = "*")
@RestController
@RequestMapping("/watermark/embed")
public class WatermarkController {


//    @RequestMapping("/download")
//    public void download(String filename, HttpServletResponse res, HttpServletRequest req) throws IOException {
//        // 设置响应流中文件进行下载
//        // attachment是以附件的形式下载，inline是浏览器打开
//        // bbb.txt是下载时显示的文件名
//        res.setHeader("Content-Disposition", "attachment;filename=277_1.pdf"); // 下载
////        res.setHeader("Content-Disposition", "inline;filename=bbb.txt"); // 浏览器打开
//        // 把二进制流放入到响应体中
//        ServletOutputStream os = res.getOutputStream();
//        System.out.println("here download");
//        String path = "D:/Desktop/277.pdf";
//
//        System.out.println("path is: " + path);
//        System.out.println("fileName is: " + filename);
//        File file = new File(path);
//        byte[] bytes = FileUtils.readFileToByteArray(file);
//        os.write(bytes);
//        os.flush();
//        os.close();
//    }

    @PostMapping("/visible")
    public ResponseEntity<Object> GetFileAndEmbed(@RequestParam(value = "file") MultipartFile file, FileWatermark watermark) throws Exception {

//        FileSystemResource fileSource = null;

        returnFileBody returnFilebody = new returnFileBody();
//        Uri url = new Uri();
        HttpHeaders headers = new HttpHeaders();
        //获取上传文件  MultipartFile
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);

        //将文件输出到目标的文件中
        String filePath = this.getClass().getResource("/").getPath();
        File dest = new File(filePath + originalFilename);


        System.out.println(dest);
        try {
            file.transferTo(dest);
            System.out.println("获得前端文件，上传成功");
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        System.out.println("shuiyin:" + watermark.getContent());
        System.out.println("color:" + watermark.getFontColor());
        System.out.println(watermark);
        //首先生成水印
        BufferedImage wm = GenerateWmUtil.createWaterMark(watermark);
        //格式转换BufferedImage to Image
        Image wm1 = Image.getInstance(wm, null);
        //将水印添加到pdf上
        if (originalFilename.endsWith(".pdf")) {
            System.out.println("现在进行pdf水印");
            String wmfilename = originalFilename.replaceFirst("\\.pdf$", "") + "_水印.pdf";
            System.out.println(wmfilename);
            String destpath = filePath + wmfilename;

            File file_withwm = AddwmToPdfUtil.addPDFImageWaterMark(filePath + originalFilename, destpath, wm1);


            System.out.println(file_withwm);
//            FileItem fileItem =  fileTOMultipartfile(file_withwm.getPath(),file.getName());
//            MultipartFile cMultiFile = new CommonsMultipartFile(fileItem);


            byte[] fileSource1 = FileUtils.readFileToByteArray(file_withwm);
            String base64String = Base64.getEncoder().encodeToString(fileSource1);

            returnFilebody.setFileName(wmfilename);
            returnFilebody.setBase64String(base64String);
            returnFilebody.setFileType("application/pdf");

            System.out.println(file_withwm.toPath());
            wmfilename = URLEncoder.encode(wmfilename, "UTF-8");
            System.out.println("wmfilename:" + wmfilename);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + wmfilename + "\"");
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(returnFilebody);
//        return fileResponse.success_file("水印添加成功成功",file_withwm);

        } else if (originalFilename.endsWith(".jpg") || originalFilename.endsWith(".png")) {
            System.out.println("..............现在进行图片水印...........");
            String wmfilename = originalFilename.replaceFirst("\\.jpg$", "") + "_暗&明水印.jpg";
            if (originalFilename.endsWith(".jpg")) {

                returnFilebody.setFileType("image/jpeg");
                 wmfilename = originalFilename.replaceFirst("\\.jpg$", "") + "_暗&明水印.jpg";
                returnFilebody.setFileName(wmfilename);
            } else {
                returnFilebody.setFileType("image/png");
                 wmfilename = originalFilename.replaceFirst("\\.png$", "") + "_暗&明水印.png";
                returnFilebody.setFileName(wmfilename);
            }

            System.out.println(filePath + originalFilename);
            BufferedImage image = ImageIO.read(new File(filePath + originalFilename));
            //添加好水印的图像
            BufferedImage Jpg_withwm = AddWmToPicUtils.addWaterMarktoPic(image, wm);
            //水印图像写入outputfile
            File outputFile = new File(filePath + "imageWithwm.jpg");
            System.out.println("outputfile:" + outputFile);
            //写入文件
            ImageIO.write(Jpg_withwm, "jpg", outputFile);
            //写到本地文件
            File classpath = new File("D:/desktop/images/" + wmfilename);
            ImageIO.write(Jpg_withwm, "jpg", classpath);

            System.out.println("wmfile:" + wmfilename);

            Path imagepath = Paths.get(outputFile.toString());
            Resource imageResource = new UrlResource(imagepath.toUri());
//            System.out.println(imageResource);
//            String path=filePath+"imageWithwm.jpg";
            wmfilename = URLEncoder.encode(wmfilename, "UTF-8");

            byte[] fileSource = Files.readAllBytes(Paths.get(outputFile.toString()));
            String base64String = Base64.getEncoder().encodeToString(fileSource);

//              String uri="https://4024f85r48.picp.vip/images-local/"+wmfilename;

            returnFilebody.setBase64String(base64String);


//            url.setUri(uri);

            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageResource.getFilename() + "\"");
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(returnFilebody);
        }


        return ResponseEntity.status(HttpStatus.OK).body("wrong");

    }

    @PostMapping("/both")
    public ResponseEntity<Object> embedBoth(@RequestParam("file") MultipartFile file, FileWatermark watermark) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        System.out.println("。。。。。。。。。。。。。。现在进行双重水印。。。。。。。。。。。。。。。。");
        //首先添加暗水印，返回文件对象
        returnFileBody returnFilebody = new returnFileBody();

        String targetFingerprint_str = watermark.getTargetFingerprint().toString();
        String inFileName = file.getOriginalFilename();
        String originalFilename = file.getOriginalFilename();
        String fileType = inFileName.substring(inFileName.lastIndexOf("."));
        String fileName = inFileName.substring(0, inFileName.lastIndexOf("."));
        String inFilePath = "D:/Desktop/image/in/" + "inFileName" + fileType;
        String fileName1 = "result";
        String outFilePath = "D:/Desktop/image/out/" + fileName1 + "-e" + fileType;
        String filePath = this.getClass().getResource("/").getPath();
        //写入文件
        File localFile = new File(inFilePath);
        file.transferTo(localFile);
        File fileAfterEmbedBwm = AddBlindwmTofileUtils.addBlindWm2File(inFilePath, outFilePath, fileType, targetFingerprint_str);
        System.out.println(fileAfterEmbedBwm);
        System.out.println("暗水印添加完成");

        //根据返回的文件 再添加明水印
        //首先生成水印
        BufferedImage wm = GenerateWmUtil.createWaterMark(watermark);
        //格式转换BufferedImage to Image
        Image wm1 = Image.getInstance(wm, null);

        if (originalFilename.endsWith(".pdf")) {
            System.out.println("..............现在进行pdf明水印..................");
            String wmfilename = originalFilename.replaceFirst("\\.pdf$", "") + "_暗&明水印.pdf";
            System.out.println(wmfilename);
            String destpath = filePath + wmfilename;

            File file_withwm = AddwmToPdfUtil.addPDFImageWaterMark(fileAfterEmbedBwm.getPath(), destpath, wm1);


            System.out.println(file_withwm);
//            FileItem fileItem =  fileTOMultipartfile(file_withwm.getPath(),file.getName());
//            MultipartFile cMultiFile = new CommonsMultipartFile(fileItem);


            byte[] fileSource1 = FileUtils.readFileToByteArray(file_withwm);
            String base64String = Base64.getEncoder().encodeToString(fileSource1);

            returnFilebody.setFileName(wmfilename);
            returnFilebody.setBase64String(base64String);
            returnFilebody.setFileType("application/pdf");

            System.out.println(file_withwm.toPath());
            wmfilename = URLEncoder.encode(wmfilename, "UTF-8");
            System.out.println("wmfilename:" + wmfilename);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + wmfilename + "\"");
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(returnFilebody);
//        return fileResponse.success_file("水印添加成功成功",file_withwm);

        } else if (originalFilename.endsWith(".jpg") || originalFilename.endsWith(".png")) {
            System.out.println("..............现在进行图片水印...........");
            if (fileType.equals(".jpg")) {

                returnFilebody.setFileType("image/jpeg");
                String wmfilename = originalFilename.replaceFirst("\\.jpg$", "") + "_暗&明水印.jpg";
                returnFilebody.setFileName(wmfilename);
            } else {
                returnFilebody.setFileType("image/png");
                String wmfilename = originalFilename.replaceFirst("\\.png$", "") + "_暗&明水印.png";
                returnFilebody.setFileName(wmfilename);
            }


            System.out.println(filePath + originalFilename);
            System.out.println("1111111");
            BufferedImage image = ImageIO.read(localFile);
            //添加好水印的图像
            BufferedImage Jpg_withwm = AddWmToPicUtils.addWaterMarktoPic(image, wm);
            //水印图像写入outputfile
            File outputFile = new File(filePath + "imageWithwm.jpg");
            System.out.println("outputfile:" + outputFile);
            //写入文件
            ImageIO.write(Jpg_withwm, "jpg", outputFile);
            //写到本地文件
            File classpath = new File("D:/desktop/images/" + fileName + fileType);
            ImageIO.write(Jpg_withwm, "jpg", classpath);

//            System.out.println("wmfile:" + wmfilename);

            Path imagepath = Paths.get(outputFile.toString());
            Resource imageResource = new UrlResource(imagepath.toUri());
//            System.out.println(imageResource);
//            String path=filePath+"imageWithwm.jpg";
//            wmfilename = URLEncoder.encode(wmfilename, "UTF-8");

            byte[] fileSource = Files.readAllBytes(Paths.get(outputFile.toString()));
            String base64String = Base64.getEncoder().encodeToString(fileSource);

//              String uri="https://4024f85r48.picp.vip/images-local/"+wmfilename;

            returnFilebody.setBase64String(base64String);


//            url.setUri(uri);

            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageResource.getFilename() + "\"");
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(returnFilebody);
        }


        return ResponseEntity.status(HttpStatus.OK).body("good");
    }


}










