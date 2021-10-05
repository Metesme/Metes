package com.eternal.controller;


import com.eternal.common.web.domain.AjaxResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 4:33 下午
 */

@RestController
@RequestMapping("/file")
public class FileController  {

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
            return "上传成功";
        } catch (IOException e) {
            System.out.println(e);
        }
        return "上传失败！";
    }

    @GetMapping ("/inlet")
    @ResponseBody
    public AjaxResult getInlet(){
        HashMap resultMap = new HashMap();
        resultMap.put("inlet","https://ipfs-gw.decloud.foundation/api/v0/add");
        resultMap.put("inletToken","c3Vic3RyYXRlLWNUR3dEbjZ2aW9lNHFzSmNNSzFBSkh1d2FEeXJtZTg2ZWZTUVZ4NGZoM0JKWmdEc0Q6MHg0NGRhZTQ2YjUxMWZhOTAwYzA3NjcxOTQ2NTJlY2I4NDAyNTAzMGVkYzdkMjU5MWZkOGVlNThlNzZhZDQxNTMzNTNkYWE1ZDQ0YjRjNjFmZWE3Y2MyZGVlMGMxZWY3ZGM1NzI4ZDQwYTY1ODQzZjBiZDY3ZTk5ODVlOTZkYWM4YQ==");
        resultMap.put("param","pin");
        return AjaxResult.success(resultMap);
    }


}
