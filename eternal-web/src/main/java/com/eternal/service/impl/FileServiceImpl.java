package com.eternal.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.eternal.domain.FileEntity;
import com.eternal.domain.PinTaskEntity;
import com.eternal.domain.UserKeyEntity;
import com.eternal.mapper.FileMapper;
import com.eternal.mapper.PinTaskMapper;
import com.eternal.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.invoke.LambdaMetafactory;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Ete
 * @version 1.0
 * @description: File
 * @date 2021/9/29 11:55 上午
 */
@Service
public class FileServiceImpl implements IFileService {
    @Resource
    private FileMapper fileMapper;

    @Resource
    private ExecutorService executorService;
    @Resource
    private PinTaskMapper pinTaskMapper;


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
        return  wrapper
                .orderByDesc(FileEntity::getCreateTime)
                .list();
    }

    @Override
    @Async
    public void pin(FileEntity entity)  {
        HashMap<String, String> postMap = new HashMap<>();
        postMap.put("cid", entity.getCid());
        postMap.put("name",entity.getFileName());
        //Thread.sleep(10000);
        final String jsonStr = JSONUtil.toJsonStr(postMap);
        executorService.execute(()->{
            //System.out.println(jsonStr);
            String body = HttpRequest.post("https://pin.crustcode.com/psa/pins")
                    .header(Header.AUTHORIZATION, "Bearer c3Vic3RyYXRlLWNUR3dEbjZ2aW9lNHFzSmNNSzFBSkh1d2FEeXJtZTg2ZWZTUVZ4NGZoM0JKWmdEc0Q6MHg2Y2UwMmEyY2FmNTRkNjNhYzhjNTNkYjRlNjA1MWYyZjlkYmRmOWQ1OGFiMzY0N2JlNjNlOGY0MjMzYTJjNDI5MzM4ZDk0YjkwNDY4ZWRmZWUwNjcwMjk0NDQyMGViZDZkN2EwMjczNDczY2I0YTg4ZjQ0MGVjNTM0MmI0NjQ4ZA==")
                    .body(jsonStr)
                    .timeout(300000)
                    .execute().body();
            //System.out.println(body);
            JSONObject jsonObject = JSONUtil.parseObj(body);
            PinTaskEntity pinTaskEntity = new PinTaskEntity();
            JSONObject pin = jsonObject.getJSONObject("pin");

            pinTaskEntity.setRequestId(jsonObject.getStr("requestId"));
            pinTaskEntity.setFileName( pin.getStr("name") );
            pinTaskEntity.setHash(pin.getStr("cid")  );
            String created = jsonObject.getStr("created");
            pinTaskEntity.setCreated(created);
            pinTaskMapper.insert(pinTaskEntity);
        });
    }
}
