package com.example.watermark_demo.utils;

//import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddWmToPicUtils {
    public static BufferedImage addWaterMarktoPic(BufferedImage targetImg, BufferedImage watermark) {
//         bufferedImage=null;
        try {
            int width = targetImg.getWidth(); //图片宽
            int height = targetImg.getHeight(); //图片高
            BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();

//            g.setColor(textColor); //水印颜色
//            g.setFont(new Font("微软雅黑", Font.ITALIC, fontSize));
            // 水印内容放置在右下角
//            int x = width - (text.length() + 1) * fontSize;
//            int y = height- fontSize * 2;
//            g.drawString(text, x, y);

            //读取水印,使用BufferedImage对象
//            BufferedImage image = ImageIO.read(new File("d:\\desktop\\waterMark.png"));

            g.drawImage(targetImg, 0, 0, null);
            //加一堆水印
            //g.rotate(radianAngle, (double) image.getWidth() / 2, (double) image.getHeight()/ 2);
            for (int j=0; j<4; j++) {
                for (int k=0; k<7; k++) {
                    //setAbsolutePosition 方法的参数（输出水印X轴位置，Y轴位置）
                    int x=Math.round(width/4*j+5);
                    int y=Math.round(height/7*k-25);

//                    image.setAbsolutePosition(width/4*j+5, height/7*k-25);
                    g.drawImage(watermark,x,y,null);
//                    content.addImage(image);
                }
            }


            FileOutputStream outImgStream = new FileOutputStream("D:\\Desktop\\13.jpg");
            ImageIO.write(bufferedImage, "jpg", outImgStream);
            outImgStream.flush();
            outImgStream.close();
            g.dispose();
            return bufferedImage;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }


}
