package com.qrcode.Util;

import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenWrapper;
import com.github.hui.quick.plugin.qrcode.wrapper.QrCodeOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class GitHubQrcodeUtil {
    public static void generateWithQrcode(HttpServletRequest request, HttpServletResponse response)
    throws Exception{
       //获取前端的url
        String url = request.getParameter("url");
        //生成二维码
        BufferedImage image = QrCodeGenWrapper.of(url).asBufferedImage();
        //将图片响应到客户端
        ImageIO.write(image,"png",response.getOutputStream());
    }

    public static void  generateWithQrcodeLogo(HttpServletRequest request, HttpServletResponse response)
            throws Exception{
        //获取前端的url
        String url = request.getParameter("url");
        //生成带logo的黑白二维码
        BufferedImage image = QrCodeGenWrapper.of(url)
                .setLogo(request.getPart("logo").getInputStream())
                .setLogoRate(7)//设置logo图片与二维码之间的比列，7表示logo的宽度等于二维码的1/7
                .setLogoStyle(QrCodeOptions.LogoStyle.ROUND)//设置logo图片的样式，将四角设为圆角
                .asBufferedImage();
        //将图片响应到客户端
        ImageIO.write(image,"png",response.getOutputStream());
    }

    public static void generateWithColor(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //获取前端的url
        String url = request.getParameter("url");
        //生成二维码
        BufferedImage image = QrCodeGenWrapper.of(url)
                .setDrawPreColor(Color.BLUE)//设置彩色
                .asBufferedImage();
        //将图片响应到客户端
        ImageIO.write(image,"png",response.getOutputStream());
    }


    public static void various(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //获取前端的url
        String url = request.getParameter("url");
        //生成带背景图片的二维码
//        BufferedImage image = QrCodeGenWrapper.of(url)
//                .setBgImg(request.getPart("logo").getInputStream())
//                .setBgOpacity(0.7F)//设置透明度
//                .asBufferedImage();

        //生成特殊形状的的二维码
//        BufferedImage image = QrCodeGenWrapper.of(url)
//                .setDrawEnableScale(true)//启动二维码绘制时的随访功能
//                .setDrawStyle(QrCodeOptions.DrawStyle.DIAMOND)//指定绘制样式（钻石）
//                .setBgOpacity(0.7F)//设置透明度
//                .asBufferedImage();

        //生成图片填充的的二维码
        BufferedImage image = QrCodeGenWrapper.of(url)
                .setErrorCorrection(ErrorCorrectionLevel.H)//设置二维码的错误纠正级别
                .setDrawStyle(QrCodeOptions.DrawStyle.IMAGE)//绘制二维码采用图片填充
                .addImg(1,1,request.getPart("logo").getInputStream())//添加图片
                .asBufferedImage();

        //将图片响应到客户端
        ImageIO.write(image,"png",response.getOutputStream());
    }
}
