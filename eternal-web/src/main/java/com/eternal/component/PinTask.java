package com.eternal.component;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.eternal.domain.PinTaskEntity;
import com.eternal.service.IPinTaskService;
import com.eternal.vo.PinTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class PinTask {


    public static  ExecutorService executor =  ExecutorBuilder.create()
                .setCorePoolSize(5)
                .setMaxPoolSize(10)
                .setWorkQueue(new LinkedBlockingQueue<>(100))
                .build();

    public void pinTaskList(PinTaskVo pinTaskVo){
        executor.execute(new ExeTask(pinTaskVo));
    }
}
@Component
class ExeTask implements Runnable{
    @Autowired
    private IPinTaskService pinTaskService;

    public static ExeTask exeTask;

    @PostConstruct
    public void init(){
        exeTask = this ;
    }
    @Resource
    private PinTaskVo pinTaskVo;
    public ExeTask(PinTaskVo pinTaskVo){
        this.pinTaskVo = pinTaskVo;
    }
    @Override
    public void run() {
        System.out.println(pinTaskVo.getTaskId());
        HashMap<String, String> postMap = new HashMap<>();
        postMap.put("cid", pinTaskVo.getHash());
        postMap.put("name",pinTaskVo.getFileName());
        String jsonStr = JSONUtil.toJsonStr(postMap);
        HttpRequest.post("https://pin.crustcode.com/psa/pins")
                .header(Header.AUTHORIZATION, "Bearer c3Vic3RyYXRlLWNUR3dEbjZ2aW9lNHFzSmNNSzFBSkh1d2FEeXJtZTg2ZWZTUVZ4NGZoM0JKWmdEc0Q6MHg2Y2UwMmEyY2FmNTRkNjNhYzhjNTNkYjRlNjA1MWYyZjlkYmRmOWQ1OGFiMzY0N2JlNjNlOGY0MjMzYTJjNDI5MzM4ZDk0YjkwNDY4ZWRmZWUwNjcwMjk0NDQyMGViZDZkN2EwMjczNDczY2I0YTg4ZjQ0MGVjNTM0MmI0NjQ4ZA==")
                .body(jsonStr)
                .timeout(30000)
                .execute().body();

        PinTaskEntity pinTaskEntity = new PinTaskEntity();
        pinTaskEntity.setTaskId(IdUtil.simpleUUID());
        pinTaskEntity.setHash(pinTaskVo.getHash());
        pinTaskEntity.setFileName(pinTaskVo.getFileName());
//        pinTaskEntity.setTaskStart(DateUtil.t);
        exeTask.pinTaskService.insertPinTask(pinTaskEntity);
    }
}
