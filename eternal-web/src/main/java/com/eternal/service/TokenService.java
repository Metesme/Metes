package com.eternal.service;

import cn.hutool.core.util.IdUtil;
import com.eternal.model.UserInfo;
import com.eternal.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenService {

    @Resource
    private RedisUtils redisUtils;

    public  String createToken (Long uid,String userName){
        String token = IdUtil.simpleUUID();
        UserInfo userInfo = new UserInfo();
        userInfo.setToken(token);
        userInfo.setUserid(uid);
        userInfo.setUsername(userName);
        redisUtils.set(token ,userInfo ,30000);
        return token;
    }


}
