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

    public  String createToken (String userName){
        String token = IdUtil.simpleUUID();
        UserInfo userInfo = new UserInfo();
        userInfo.setToken(token);
        userInfo.setUsername(userName);
        boolean b = redisUtils.set("ete_login_token:" + token, userInfo, 30000);
        System.out.println(b);
        return token;
    }


}
