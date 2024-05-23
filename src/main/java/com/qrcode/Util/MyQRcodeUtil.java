package com.qrcode.Util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class MyQRcodeUtil {
    private static final int CODE_WIDTH = 300;
    //CODE_HEIGHT：二维码高度，单位像素
    private static final int CODE_HEIGHT = 300;
    //FRONT_COLOR：二维码前景色，0x000000 表示黑色
    private static final int FRONT_COLOR = 0xFF000000;
    //BACKGROUND_COLOR：二维码背景色，0xFFFFFF 表示白色
    //演示用 16 进制表示，和前端页面 CSS 的取色是一样的，注意前后景颜色应该对比明显，如常见的黑白
    private static final int BACKGROUND_COLOR = 0xFFFFFFFF;
    //编码字符集
    private static final String Type_Encoding = "utf-8";

    //生成黑白二维码
    public static void getBUfferedImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //使用谷歌提供的zxing开源库，生成普通的黑白二维码
        //获取前端的url，即要生成的二维码的内荣
        String url = request.getParameter("url");
        //创建一个Map集合，存储二维码相关的属性
        Map map=new HashMap();
        //设置二维码的误差校正级别 这里选择H的容错率，最多可有约30%的坏像素
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码的字符集
        map.put(EncodeHintType.CHARACTER_SET, Type_Encoding);
        //设置二维码四周的留白
        map.put(EncodeHintType.MARGIN, 1);

        //多格式写入程序
        //可以根据传入的BarcodeFormat参数，生成对应类型的二维码
        MultiFormatWriter writer =new MultiFormatWriter();
        //writer.encode(内容，什么格式的二维码，宽度，高度，参数（map里面存有）)
        //位矩阵对象（对象内部实际上是一个二维码，二维数组中的每一个元素都是boolean类型，true代表黑色，false代表白色）
        BitMatrix bitMatrix=writer.encode(url, BarcodeFormat.QR_CODE,CODE_WIDTH,CODE_HEIGHT,map);
        //获取矩阵的宽度
        int width =bitMatrix.getWidth();
        //获取矩阵的高度
        int hight =bitMatrix.getHeight();
        //生成二维码图像
        BufferedImage image=new BufferedImage(width,hight,BufferedImage.TYPE_INT_RGB);
        //遍历位矩阵对象
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < hight; y++) {
                //颜色值
                image.setRGB(x,y,bitMatrix.get(x,y)?FRONT_COLOR:BACKGROUND_COLOR);
            }
        }
        //将图片响应到客户端
        ServletOutputStream out=response.getOutputStream();
        ImageIO.write(image,"png",out);
        out.flush();
        out.close();
    }
    //生成黑白二维码
    public static void generateWithLogo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //使用谷歌提供的zxing开源库，生成普通的黑白二维码
        //获取前端的url，即要生成的二维码的内荣
        String url = request.getParameter("url");
        //创建一个Map集合，存储二维码相关的属性
        Map map=new HashMap();
        //设置二维码的误差校正级别 这里选择H的容错率，最多可有约30%的坏像素
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码的字符集
        map.put(EncodeHintType.CHARACTER_SET, Type_Encoding);
        //设置二维码四周的留白
        map.put(EncodeHintType.MARGIN, 1);

        //多格式写入程序
        //可以根据传入的BarcodeFormat参数，生成对应类型的二维码
        MultiFormatWriter writer =new MultiFormatWriter();
        //writer.encode(内容，什么格式的二维码，宽度，高度，参数（map里面存有）)
        //位矩阵对象（对象内部实际上是一个二维码，二维数组中的每一个元素都是boolean类型，true代表黑色，false代表白色）
        BitMatrix bitMatrix=writer.encode(url, BarcodeFormat.QR_CODE,CODE_WIDTH,CODE_HEIGHT,map);
        //获取矩阵的宽度
        int width =bitMatrix.getWidth();
        //获取矩阵的高度
        int hight =bitMatrix.getHeight();

        //生成二维码图像
        BufferedImage image=new BufferedImage(width,hight,BufferedImage.TYPE_INT_RGB);
        //遍历位矩阵对象
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < hight; y++) {
                //颜色值
                image.setRGB(x,y,bitMatrix.get(x,y)?FRONT_COLOR:BACKGROUND_COLOR);
            }
        }


        //添加logo
        //第一部分 将logo缩放 Part代表是form的一部分
        //获取logo图片
        Part logopart =request.getPart("logo");
        InputStream inputStream = logopart.getInputStream();
        Image logoImage = ImageIO.read(inputStream);
        //对获取的logo进行缩放
        int logoWidth = logoImage.getWidth(null);
        int logoHeight = logoImage.getHeight(null);
        if (logoWidth>60 ){logoWidth =60;}
        if (logoHeight>60) {logoHeight=60;}

        //缩放核心代码 Image.SCALE_SMOOTH 平滑缩放算法对原始的图像进行缩放
        Image scaledLogo = logoImage.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);

        //第二部分，将缩放的logo滑到黑白二维码上
        //先获取一个2D的画笔
        Graphics2D graphics2D=image.createGraphics();
        //指定从哪开始画，x y 坐标
        int x=(300-logoWidth)/2;
        int y =(300-logoHeight)/2;
        //将缩放之后的logo画上去
        graphics2D.drawImage(scaledLogo,x,y,null);
        //使正方形的图片变为圆角
        Shape shape=new RoundRectangle2D.Float(x,y,logoWidth,logoHeight,10,10);
        //使用宽度为4像素的笔触
        graphics2D.setStroke(new BasicStroke(4f));
        //给logo画圆角矩形
        graphics2D.draw(shape);
        //释放画笔
        graphics2D.dispose();



        //将图片响应到客户端
        ServletOutputStream out=response.getOutputStream();
        ImageIO.write(image,"png",out);
        out.flush();
        out.close();
    }

}
