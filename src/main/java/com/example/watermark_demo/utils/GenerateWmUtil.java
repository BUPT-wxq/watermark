package com.example.watermark_demo.utils;

import com.example.watermark_demo.data.entity.FileWatermark;
import com.example.watermark_demo.data.entity.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GenerateWmUtil {

    public static BufferedImage createWaterMark(FileWatermark watermark) {
        String content=watermark.getContent();
        String fontColor=watermark.getFontColor();
        int fontSize=watermark.getFontSize();
        int frameSize=watermark.getFrameSize();
        double alpha=watermark.getAlpha();
        int alpha1=(int)alpha;
        int angle=watermark.getAngle();
        double theta=Math.toRadians(angle);



        //生成图片宽度
        int width = 250;
        //生成图片高度
        int heigth = 160;
        //获取bufferedImage对象
        BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
        //得到画笔对象
        Graphics2D g2d = image.createGraphics();
        //使得背景透明
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, heigth, Transparency.TRANSLUCENT);
        g2d.dispose();
        g2d = image.createGraphics();
        //设置对线段的锯齿状边缘处理
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //设置水印旋转，倾斜度
        g2d.rotate(theta, (double) image.getWidth()/2, (double) image.getHeight()/2);
        //设置颜色，这是黑色，第4个参数是透明度
        String[] rgbArray = fontColor.replaceAll("[()\\s+]", "").split(",");
        // 将字符串数组中的数字转换为 int 类型
        int r = Integer.parseInt(rgbArray[0]);
        int g = Integer.parseInt(rgbArray[1]);
        int b = Integer.parseInt(rgbArray[2]);
        g2d.setColor(new Color(r, g, b, alpha1));
        //设置字体
        Font font = new Font("宋体", Font.ROMAN_BASELINE, fontSize+14);
        g2d.setFont(font);

        //计算绘图偏移x、y，使得使得水印文字在图片中居中
        float x = 0.5f * fontSize;
        float y = 0.5f * heigth + x;
        //取绘制的字串宽度、高度中间点进行偏移，使得文字在图片坐标中居中
        g2d.drawString(content, x, y);
        //释放资源
        g2d.dispose();
        return image;
    }



}
