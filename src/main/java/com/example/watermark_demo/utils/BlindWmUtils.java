package com.example.watermark_demo.utils;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static org.opencv.core.Core.BORDER_CONSTANT;
import static org.opencv.core.Core.copyMakeBorder;
import static org.opencv.core.CvType.CV_8U;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class BlindWmUtils {
    public static Mat read(String image, int type) {
        Mat src = imread(image, type);
        if (src.empty()) {
            System.out.println("File not found!");
            System.exit(-1);
        }
        return src;
    }

    public static boolean isAscii(String str) {
        return "^[ -~]+$".matches(str);
    }

    public static void fixSize(Mat src, Mat mirror) {
        if (src.rows() != mirror.rows()) {
            copyMakeBorder(src, src, 0, mirror.rows() - src.rows(),
                    0, 0, BORDER_CONSTANT, Scalar.all(0));
        }
        if (src.cols() != mirror.cols()) {
            copyMakeBorder(src, src, 0, 0,
                    0, mirror.cols() - src.cols(), BORDER_CONSTANT, Scalar.all(0));
        }
    }

    public static Mat drawNonAscii(String watermark) {
        StringBuilder wm = new StringBuilder();
        for (int i = 0; i < watermark.length(); i++) {
            wm.append(watermark.charAt(i));

            // 插入换行符的条件
            if ((i + 1) % 5 == 0 && i + 1 != watermark.length()) {
                wm.append("\n");
            }
        }
        String new_wm = wm.toString();
        System.out.println(new_wm);
        Font font = new Font("Default", Font.PLAIN, 64);
        FontMetrics metrics = new Canvas().getFontMetrics(font);
        int maxWidth = 0;

        // 分割字符串为行
        String[] lines = new_wm.split("\n");

        // 计算每行的宽度并找到最大值
        for (String line : lines) {
            int lineWidth = metrics.stringWidth(line);
            maxWidth = Math.max(maxWidth, lineWidth);
        }
        System.out.println(maxWidth);
//        int width = metrics.stringWidth(new_wm);
//        System.out.println(width);
        int height = metrics.getHeight()*lines.length;
//        System.out.println(lines.length);

        BufferedImage bufferedImage = new BufferedImage(maxWidth, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        for(int i = 0; i<lines.length; i++){
            graphics.drawString(lines[i], 0, metrics.getAscent()+i*metrics.getHeight());
        }

        graphics.dispose();
        byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        Mat res = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CV_8U);
        res.put(0, 0, pixels);
        return res;
    }
}
