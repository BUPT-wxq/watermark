package com.example.watermark_demo.utils;

import com.example.watermark_demo.Service.iml.ImageTranfer;
import com.example.watermark_demo.Service.iml.PdfTransfer;
import com.example.watermark_demo.converter.Converter;
import com.example.watermark_demo.converter.DctConverter;
import com.example.watermark_demo.dencoder.Encoder;
import com.example.watermark_demo.dencoder.TextEncoder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddBlindwmTofileUtils {

    public static File addBlindWm2File(String inFilePath, String outFilePath, String fileType,String targetFingerprint_str) throws Exception {
        System.out.println("工具类调用中............");
        Converter converter = new DctConverter();
        Encoder encoder = new TextEncoder(converter);

        if (fileType.equals(".pdf")) {
            PdfTransfer pdfTranfer = new PdfTransfer();


            List<byte[]> ins = pdfTranfer.pdf2Image(inFilePath, "png", 1);
            String inPath = "D:/Desktop/image-1/in/";
            String outPath = "D:/Desktop/image-1/out/";
            for (int i = 0; i < ins.size(); i++) {
                byte[] data = ins.get(i);
                FileUtils.writeByteArrayToFile(new File(inPath + i + ".png"), data);
                if(i%5 == 0){
                    encoder.encode(inPath + i + ".png", targetFingerprint_str, outPath + i + "-e.png");
                }
            }
            ImageTranfer tranfer = new ImageTranfer();
            List<String> imagePath = new ArrayList<String>();
            for (int i = 0; i < ins.size(); i++) {
                if(i%5 == 0){
                    imagePath.add(outPath + i + "-e.png");
                }
                else{
                    imagePath.add(inPath + i + ".png");
                }

            }
            byte[] data = tranfer.image2PdfByPath(imagePath);
            //将二进制转为pdf后存入outFilepath
            FileUtils.writeByteArrayToFile(new File(outFilePath), data);

        } else {
            encoder.encode(inFilePath, targetFingerprint_str, outFilePath);
        }
        System.out.println("暗水印服务即将完成");
       return new File(outFilePath);
    }
}
