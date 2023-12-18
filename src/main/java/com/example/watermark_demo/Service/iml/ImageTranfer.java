package com.example.watermark_demo.Service.iml;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.itextpdf.text.Rectangle;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;

public class ImageTranfer {
    public byte[] image2PdfByPath(List<String> imagePath) throws Exception{
        List<byte[]> imgList = new ArrayList<byte[]>();
        for (int i = 0; i < imagePath.size(); i++) {
            String imgPath = imagePath.get(i);
            if(imgPath!=null || !"".equals(imgPath)){
                File imgFile = new File(imgPath);
                imgList.add(FileUtils.readFileToByteArray(imgFile));
            }
        }
        String imgPath = imagePath.get(0);
        File imgFile = new File(imgPath);
        BufferedImage img = ImageIO.read(imgFile);
        float width = img.getWidth();
        float height = img.getHeight();
//        if(width>height){
//            width = (float)(width*0.6);
//            height = (float)(height*0.6);
//        }
        return imageExtraction(imgList, width, height);
    }

//    /**
//     * 图片输入流转换成pdf输入流
//     * @param imageInputStream 图片输入流
//     * @return byte[]
//     * @throws Exception
//     */
//    public byte[] image2PdfByInputStream(List<InputStream> imageInputStream) throws Exception{
//        List<byte[]> imgList = new ArrayList<byte[]>();
//        for (int i = 0; i < imageInputStream.size(); i++) {
//            InputStream in = imageInputStream.get(i);
//            if(in!=null){
//                imgList.add(IOUtils.toByteArray(in));
//            }
//            in.close();
//        }
//        return imageExtraction(imgList);
//    }
//
//    /**
//     * 图片字节数组转换成pdf输入流
//     * @param imageByte 图片字节数组
//     * @return byte[]
//     * @throws Exception
//     */
//    public byte[] image2PdfByByte(List<byte[]> imageByte) throws Exception{
//        return imageExtraction(imageByte);
//    }

    //*********************************image to pdf **********************************************************
    private byte[] imageExtraction(List<byte[]> bs, float width, float height) throws Exception{
        // setup two threads to handle image extraction.
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        try {
            //将字节转换成Image类型
            List<Callable<Image>> callables = new ArrayList<Callable<Image>>(bs.size());
            for (int i = 0; i < bs.size(); i++) {
                callables.add(new CapturePage(bs.get(i)));
            }
            List<Future<Image>> listFuture = executorService .invokeAll(callables);
            executorService.submit(new ListClear(bs)).get();
//            Image img = (Image) listFuture.get(0);
//            Rectangle rectangle = new Rectangle(img.getWidth(),img.getHeight());
            Rectangle rectangle = new Rectangle(width, height);
            //一页一张图片形成pdf
            Document doc = new Document(rectangle, 0, 0, 0, 0);
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            // 将图片转成pdf并写到本地
            PdfWriter.getInstance(doc, bout);
            doc.open();
            for (Future<Image> future : listFuture) {
                doc.newPage();
                doc.add(future.get());
            }
            doc.close();
            byte[] result = bout.toByteArray();
            bout.flush();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return null;
    }

    public class CapturePage implements Callable<Image> {
        private byte[] data;
        private CapturePage(byte[] data) {
            this.data = data;
        }
        public Image call() throws Exception{
            Image imageRight = Image.getInstance(data);
            float height = imageRight.getHeight();
            float width = imageRight.getWidth();
            int p = 0;
            float p2 = 0.0f;
            p2 = 530 / width * 100;
            p = Math.round(p2)+3;
            // 设置图片居中显示
            imageRight.setAlignment(Image.MIDDLE);
//            imageRight.scalePercent(p);// 表示是原来图像的比例;
            return imageRight;
        }
    }

    public class ListClear implements Callable<Void> {
        private List<byte[]> bs;
        private ListClear(List<byte[]> bs) {
            this.bs = bs;
        }
        public Void call() {
            if (bs != null) {
                bs.clear();
                System.out.println("bs Clear");
            }
            return null;
        }
    }
}
