package com.example.watermark_demo.utils;

import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

public class AddwmToPdfUtil {
    public static File addPDFImageWaterMark(String srcPath, String destPath, Image image)
            throws Exception {

        PdfReader reader = new PdfReader(srcPath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(destPath));

        PdfGState gs = new PdfGState();
        //gs.setFillOpacity(0.2f);//图片水印透明度
        //gs.setStrokeOpacity(0.4f);//设置笔触字体不透明度
        PdfContentByte content = null;

        int total = reader.getNumberOfPages();//pdf文件页数
        for (int i=0; i<total; i++) {
            float x = reader.getPageSize(i+1).width();//页宽度
            float y = reader.getPageSize(i+1).height();//页高度
            content = stamper.getOverContent(i+1);
            content.setGState(gs);
            content.beginText();//开始写入

            //每页7行，一行5个
            for (int j=0; j<4; j++) {
                for (int k=0; k<7; k++) {
                    //setAbsolutePosition 方法的参数（输出水印X轴位置，Y轴位置）
                    image.setAbsolutePosition(x/4*j+5, y/7*k-25);
                    content.addImage(image);
                }
            }
            content.endText();//结束写入
        }
        //关闭流
        stamper.close();
        reader.close();
        return new File(destPath);
    }

}
