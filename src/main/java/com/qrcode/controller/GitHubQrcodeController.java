package com.qrcode.controller;

import com.qrcode.Util.GitHubQrcodeUtil;
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
public class GitHubQrcodeController {
    @RequestMapping("/generateWithQrcode")
    public String generateWithQrcode(HttpServletRequest request, HttpServletResponse response)
    throws  Exception{
        GitHubQrcodeUtil.generateWithQrcode(request,response);
        return "ok";
    }

    @RequestMapping("/generateWithQrcodeLogo")
    public String generateWithQrcodeLogo(HttpServletRequest request, HttpServletResponse response)
            throws  Exception{
        GitHubQrcodeUtil.generateWithQrcodeLogo(request,response);
        return "ok";
    }

    @RequestMapping("/generateWithColor")
    public String generateWithColor(HttpServletRequest request, HttpServletResponse response)
            throws  Exception{
        GitHubQrcodeUtil.generateWithColor(request,response);
        return "ok";
    }


    @RequestMapping("/various")
    public String various(HttpServletRequest request, HttpServletResponse response)
            throws  Exception{
        GitHubQrcodeUtil.various(request,response);
        return "ok";
    }
}
