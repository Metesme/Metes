package com.eternal.service;

import cn.hutool.core.util.IdUtil;
import com.eternal.vo.UserLoginVo;
import com.eternal.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenService {

    @Resource
    private RedisUtils redisUtils;

    public  String createToken (String userName ,Long userId){
        String token = IdUtil.simpleUUID();
        UserLoginVo userInfo = new UserLoginVo();
        userInfo.setToken(token);
        userInfo.setUsername(userName);
        userInfo.setUserid(userId);
        boolean b = redisUtils.set("ete_login_token:" + token, userInfo, 30000);
        System.out.println(b);
        return token;
    }


}
