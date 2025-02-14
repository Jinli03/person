/**
 * 功能：
 * 作者：JInli
 * 日期： 2025/2/11 22:24
 */
package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.exception.CustomException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String filePath = System.getProperty("user.dir") + "/files/";// D:\vue\files

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        String originFilename = file.getOriginalFilename(); //xxx.png
        System.out.println("1");
        if (!FileUtil.isDirectory(filePath)) {//当前文件父级目录不存在，则创建目录
            FileUtil.mkdir(filePath);
        }
        System.out.println("1");
        String fileName = System.currentTimeMillis() + "_" + originFilename;
        String realPath = filePath + fileName;
        try {
            FileUtil.writeBytes(file.getBytes(), realPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("500", "文件上传失败");
        }
        String url = "http://localhost:9090/files/download/" + fileName;
        return Result.success(url);
    }

    /**
     * 文件下载
     */
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) {
        try {
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));//下载
            response.setContentType("application/octet-stream");
            OutputStream os = response.getOutputStream();
            String realPath = filePath + fileName;
            byte[] bytes = FileUtil.readBytes(realPath);
            os.write(bytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("500", "文件下载失败");
        }
    }
}
