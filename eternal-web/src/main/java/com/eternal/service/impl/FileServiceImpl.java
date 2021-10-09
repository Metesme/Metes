package com.eternal.service.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.eternal.domain.FileEntity;
import com.eternal.domain.UserKeyEntity;
import com.eternal.mapper.FileMapper;
import com.eternal.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.LambdaMetafactory;
import java.util.HashMap;
import java.util.List;

/**
 * @author Hikari
 * @version 1.0
 * @description: TODO
 * @date 2021/9/29 11:55 上午
 */
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public int insert(FileEntity entity) {

        return fileMapper.insert(entity);
    }

    @Override
    public List<FileEntity> selectList(FileEntity entity) {
        LambdaQueryChainWrapper<FileEntity> wrapper = new LambdaQueryChainWrapper<>(fileMapper)
                .eq(FileEntity::getUserId, entity.getUserId());
        String fileName = entity.getFileName();
        if (fileName != null){
            wrapper.eq(FileEntity::getFileName, fileName);
        }
        List<FileEntity> list = wrapper
                .orderByDesc(FileEntity::getCreateTime)
                .list();
        return list;
    }

    @Override
    public String pin(FileEntity entity) {
        HashMap<String, String> postMap = new HashMap<>();
        postMap.put("cid", entity.getCid());
        postMap.put("name",entity.getFileName());
        String jsonStr = JSONUtil.toJsonStr(postMap);
        String s = HttpRequest.post("https://pin.crustcode.com/psa/pins")
                .header(Header.AUTHORIZATION, "Bearer c3Vic3RyYXRlLWNUR3dEbjZ2aW9lNHFzSmNNSzFBSkh1d2FEeXJtZTg2ZWZTUVZ4NGZoM0JKWmdEc0Q6MHg2Y2UwMmEyY2FmNTRkNjNhYzhjNTNkYjRlNjA1MWYyZjlkYmRmOWQ1OGFiMzY0N2JlNjNlOGY0MjMzYTJjNDI5MzM4ZDk0YjkwNDY4ZWRmZWUwNjcwMjk0NDQyMGViZDZkN2EwMjczNDczY2I0YTg4ZjQ0MGVjNTM0MmI0NjQ4ZA==")
                .body(jsonStr)
                .timeout(30000)
                .execute().body();
        return s;
    }
}
