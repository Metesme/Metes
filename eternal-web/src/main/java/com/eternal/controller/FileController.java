package com.eternal.controller;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.eternal.common.annotation.CurrentUser;
import com.eternal.common.web.controller.BaseController;
import com.eternal.common.web.domain.AjaxResult;
import com.eternal.common.web.page.TableDataInfo;
import com.eternal.domain.FileEntity;
import com.eternal.vo.UserLoginVo;
import com.eternal.service.IFileService;
import com.eternal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
    @Autowired
    private IUserService userService;

//    @PostMapping("/upload")
//    @ResponseBody
//    public String upload(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            System.out.println("error");
//            return "上传失败，请选择文件";
//        }
//        String fileName = file.getOriginalFilename();
//        String filePath = "/Users/jiajunmei/Desktop/";
//        File dest = new File(filePath + fileName);
//        try {
//            file.transferTo(dest);
//            return "上传成功";
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//        return "上传失败！";
//    }

    @GetMapping ("/inlet")
    public AjaxResult getInlet(){
        HashMap resultMap = new HashMap();
        resultMap.put("inlet","https://ipfs-gw.decloud.foundation/api/v0/add");
        resultMap.put("inletToken","c3Vic3RyYXRlLWNUR3dEbjZ2aW9lNHFzSmNNSzFBSkh1d2FEeXJtZTg2ZWZTUVZ4NGZoM0JKWmdEc0Q6MHg0NGRhZTQ2YjUxMWZhOTAwYzA3NjcxOTQ2NTJlY2I4NDAyNTAzMGVkYzdkMjU5MWZkOGVlNThlNzZhZDQxNTMzNTNkYWE1ZDQ0YjRjNjFmZWE3Y2MyZGVlMGMxZWY3ZGM1NzI4ZDQwYTY1ODQzZjBiZDY3ZTk5ODVlOTZkYWM4YQ==");
        resultMap.put("param","pin");
        return AjaxResult.success(resultMap);
    }



    @GetMapping("list")
    public TableDataInfo getFileList(@CurrentUser UserLoginVo user, FileEntity entity, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        startPage();
        entity.setUserId(user.getUserid());
        return getDataTable(fileService.selectList(entity));
    }

    @PostMapping("pinByHash")
    public AjaxResult pinByHash(@CurrentUser UserLoginVo user, FileEntity entity, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        String s = fileService.pin(entity);
       // JSON resJson = JSONUtil.parse(s);
        entity.setUserId(user.getUserid());
        int insert = fileService.insert(entity);
        return AjaxResult.success();
    }
}
