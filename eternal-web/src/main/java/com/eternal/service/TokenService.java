package com.eternal.service;

import cn.hutool.core.util.IdUtil;
import com.eternal.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenService {

    @Resource
    private RedisUtils redisUtils;

    public  String createToken (String uid){
        String token = IdUtil.simpleUUID();
        redisUtils.set(token ,uid ,30000);
        return token;
    }


}
