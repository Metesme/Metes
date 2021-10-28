package com.eternal.controller;



import com.eternal.common.annotation.CurrentUser;
import com.eternal.common.web.controller.BaseController;
import com.eternal.common.web.domain.AjaxResult;
import com.eternal.common.web.page.TableDataInfo;
import com.eternal.domain.FileEntity;
import com.eternal.vo.UserLoginVo;
import com.eternal.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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


    @GetMapping ("/inlet")
    public AjaxResult getInlet(){
        HashMap<String,String> resultMap = new HashMap<>(16);
        resultMap.put("inlet","https://ipfs-gw.decloud.foundation/api/v0/add");
        resultMap.put("inletToken","c3Vic3RyYXRlLWNUR3dEbjZ2aW9lNHFzSmNNSzFBSkh1d2FEeXJtZTg2ZWZTUVZ4NGZoM0JKWmdEc0Q6MHg0NGRhZTQ2YjUxMWZhOTAwYzA3NjcxOTQ2NTJlY2I4NDAyNTAzMGVkYzdkMjU5MWZkOGVlNThlNzZhZDQxNTMzNTNkYWE1ZDQ0YjRjNjFmZWE3Y2MyZGVlMGMxZWY3ZGM1NzI4ZDQwYTY1ODQzZjBiZDY3ZTk5ODVlOTZkYWM4YQ==");
        resultMap.put("param","pin");
        return AjaxResult.success(resultMap);
    }

    @GetMapping("del")
    public AjaxResult deleteFile(@CurrentUser UserLoginVo user, FileEntity entity){
        entity.setUserId(user.getUserid());
        if(fileService.deleteFile(entity)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    @GetMapping("list")
    public TableDataInfo getFileList(@CurrentUser UserLoginVo user, FileEntity entity){
        startPage();
        entity.setUserId(user.getUserid());
        return getDataTable(fileService.selectList(entity));
    }

    @PostMapping("pinByHash")
    public AjaxResult pinByHash(@CurrentUser UserLoginVo user,@RequestBody FileEntity entity)  {
        entity.setUserId(user.getUserid());
        entity.setCreateBy(user.getUsername());
        entity.setCreateTime(new Date());
        fileService.insert(entity);
        fileService.pin(entity);
        return AjaxResult.success();
    }

}
