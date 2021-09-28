package com.eternal.controller;

import com.eternal.common.web.controller.BaseController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 4:33 下午
 */
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        String fileName = file.getOriginalFilename();
        String filePath = "/Users/jiajunmei/Desktop/";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (IOException e) {
            System.out.println(e);
        }
        return "上传失败！";
    }
}
