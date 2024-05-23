package com.qrcode.controller;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//设置文件大小
@MultipartConfig(fileSizeThreshold = 1024*1024*2,
        maxFileSize = 1024*1024*10,
        maxRequestSize = 1024*1024*100)

public class MyQRcodeController {
    //生成黑白二维码
    @RequestMapping("/generate")
    public String qr(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        com.qrcode.Util.MyQRcodeUtil.getBUfferedImage(request, response);
        return  "ok";
    }
    //生成带logo的黑白二维码
    @RequestMapping("/generateWithLogo")
    public String generateWithLogo(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        com.qrcode.Util.MyQRcodeUtil.generateWithLogo(request, response);
        return  "ok";
    }



}
