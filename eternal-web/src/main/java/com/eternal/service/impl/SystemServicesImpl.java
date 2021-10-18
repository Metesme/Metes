package com.eternal.service.impl;

import com.eternal.service.ISystemService;
import com.eternal.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SystemServicesImpl implements ISystemService {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public Boolean isIpLimit(String ip) {
        Integer o = (Integer)redisUtils.get(ip);
        if(o == null){
            redisUtils.set(ip,1,60);
            return false;
        }

        if( o < 10 ){
            redisUtils.incr(ip,1);
            return false;
        }
        return true;
    }
}
