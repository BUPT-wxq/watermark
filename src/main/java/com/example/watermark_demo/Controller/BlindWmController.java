package com.example.watermark_demo.Controller;


import com.example.watermark_demo.converter.Converter;
import com.example.watermark_demo.converter.DctConverter;
import com.example.watermark_demo.data.entity.Uri;
import com.example.watermark_demo.data.entity.returnFileBody;
import com.example.watermark_demo.dencoder.Decoder;
import com.example.watermark_demo.dencoder.Encoder;
import com.example.watermark_demo.Service.iml.PdfTransfer;
import com.example.watermark_demo.Service.iml.ImageTranfer;
import com.example.watermark_demo.dencoder.TextEncoder;
import com.example.watermark_demo.utils.AddBlindwmTofileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import com.fzl.response.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.ArrayList;

@Slf4j
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/watermark")

public class BlindWmController {
    static {
        Loader.load(opencv_java.class);
    }
    @PostMapping(value = "/embed/invisible")
    public ResponseEntity<Object> embedBlindWm(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam("targetFingerprint") List targetFingerprint
    ) throws Exception {
        returnFileBody returnFilebody = new returnFileBody();

//        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        String targetFingerprint_str = targetFingerprint.toString();
//        Converter converter = new DctConverter();
//        Encoder encoder = new TextEncoder(converter);
        String inFileName = file.getOriginalFilename();
        String fileType = inFileName.substring(inFileName.lastIndexOf("."));
        String fileName = inFileName.substring(0, inFileName.lastIndexOf("."));
        String fileName1="result";
        String inFilePath = "D:/Desktop/image/in/" + "inFileName"+fileType;
        String outFilePath = "D:/Desktop/image/out/" + fileName1 + "-e" + fileType;
        //写入文件
        File localFile = new File(inFilePath);

        file.transferTo(localFile);
        //此处调用工具类，传参inFilePath，outfilepath，targetFingerprint_str，filetype
        File fileAfterEmbedBwm= AddBlindwmTofileUtils.addBlindWm2File(inFilePath,outFilePath,fileType,targetFingerprint_str);
        System.out.println(fileAfterEmbedBwm);

        byte[] dwBytes = Files.readAllBytes(fileAfterEmbedBwm.toPath());
        String base64String = Base64.getEncoder().encodeToString(dwBytes);

        HttpHeaders headers = new HttpHeaders();
        returnFilebody.setBase64String(base64String);
        if (fileType.equals(".pdf")) {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=加水印的pdf.pdf");
//            returnFilebody.setBase64String(base64String);
            returnFilebody.setFileType("application/pdf");
            returnFilebody.setFileName(fileName + "_暗水印.pdf");
            return ResponseEntity.status(HttpStatus.OK).headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(returnFilebody);
        } else if (fileType.equals(".jpg")) {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=加水印的图片.jpg");
            returnFilebody.setFileType("application/jpeg");
            returnFilebody.setFileName(fileName + "_暗水印.jpg");
            return ResponseEntity.status(HttpStatus.OK).headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(returnFilebody);
        } else if (fileType.equals(".png")) {
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=加水印的图片.png");
            returnFilebody.setFileType("application/png");
            returnFilebody.setFileName(fileName + "_暗水印.png");
            return ResponseEntity.status(HttpStatus.OK).headers(headers)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(returnFilebody);
        }


        return ResponseEntity.status(HttpStatus.OK).body("文件格式错误！");

    }

    @PostMapping(value = "/extract")
    public ResponseEntity<Object> extractBlindWm(
            @RequestParam("file") MultipartFile file
    ) throws Exception {
//        returnFileBody returnFilebody = new returnFileBody();
        HttpHeaders headers = new HttpHeaders();
        Converter converter = new DctConverter();

        Decoder decoder = new Decoder(converter);
        String inFileName = file.getOriginalFilename();
        String fileType = inFileName.substring(inFileName.lastIndexOf("."));
        String fileName = inFileName.substring(0, inFileName.lastIndexOf("."));
        String fileName1="result";
        String inFilePath = "D:/Desktop/image/in/" + "inFileName"+fileType;

        String outFilePath = "D:/Desktop/image/out/" + fileName1+ "-d" + fileType;
        File localFile = new File(inFilePath);

        //文件写入本地
        file.transferTo(localFile);


        if (fileType.equals(".pdf")) {
            PdfTransfer pdfTranfer = new PdfTransfer();
            List<byte[]> ins = pdfTranfer.pdf2Image(inFilePath, "png", 1);
            String inPath = "D:/Desktop/image-2/in/";
            String outPath = "D:/Desktop/image-2/out/";
            for (int i = 0; i < ins.size(); i++) {
                byte[] data = ins.get(i);
                FileUtils.writeByteArrayToFile(new File(inPath + i + ".png"), data);
                if(i%5 == 0){
                    decoder.decode(inPath + i + ".png", outPath + i + "-d.png");
                }


            }
            outFilePath = outPath + "0-d.png";
//            ImageTranfer tranfer = new ImageTranfer();
//            List<String> imagePath = new ArrayList<String>();
//            for(int i = 0; i < ins.size(); i++){
//                imagePath.add(outPath+i+"-d.png");
//            }
//            byte[] data = tranfer.image2PdfByPath(imagePath);
//            FileUtils.writeByteArrayToFile(new File(outFilePath), data);
        } else {
            decoder.decode(inFilePath, outFilePath);
        }
        System.out.println(outFilePath);
        byte[] dwBytes = Files.readAllBytes(Paths.get(outFilePath));
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(dwBytes));
        File classpath=new File("D:/desktop/images/"+"暗水印.png");
        ImageIO.write(image,"png",classpath);
        String wmfilename= URLEncoder.encode("暗水印.png","UTF-8");
        String uri="https://4024f85r48.picp.vip/images-local/"+wmfilename;
        Uri url=new Uri();
        url.setUri(uri);
//        String base64String = Base64.getEncoder().encodeToString(dwBytes);
//        EmbedBlindWmResponse response = EmbedBlindWmResponse.builder()
//                .statusCode("200")
//                .statusContent("成功")
//                .file(outFile)
//                .build();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invisibleWatermark.png");
//        returnFilebody.setFileName("提取的暗水印.png");
//        returnFilebody.setBase64String(base64String);
//        returnFilebody.setFileType("application/png");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .body(url);

    }
}
