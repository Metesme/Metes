package com.eternal.controller;


import com.eternal.common.annotation.PassToken;
import com.eternal.common.web.controller.BaseController;
import com.eternal.common.web.page.TableDataInfo;
import com.eternal.domain.FileEntity;
import com.eternal.mapper.FileMapper;
import com.eternal.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            System.out.println("error");
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = "/Users/jiajunmei/Desktop/";
        File dest = new File(filePath + fileName);

        try {
            file.transferTo(dest);
            FileEntity entity = new FileEntity();
            entity.setFileName(fileName);
            entity.setFileSize(file.getSize());
            entity.setFilePath(filePath);
            fileService.insert(entity);
            return "上传成功";
        } catch (IOException e) {
            System.out.println(e);
        }

        return "上传失败！";
    }
    @GetMapping("list")
    @PassToken
    public TableDataInfo getFileList(FileEntity entity){
        startPage();
        return getDataTable(fileService.selectList(entity));
    }
}
