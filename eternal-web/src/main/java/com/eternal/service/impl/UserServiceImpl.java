package com.eternal.service.impl;

import com.eternal.domain.UserEntity;
import com.eternal.mapper.UserMapper;
import com.eternal.model.UserInfo;
import com.eternal.service.IUserService;
import com.eternal.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/24 2:20 下午
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisUtils redisUtils;
    @Override
    public List<UserEntity> selectUserList(UserEntity entity) {
        return null;
    }

    @Override
    public Boolean checkToken(String token, UserEntity user) {
        UserInfo userInfo;
        userInfo = (UserInfo)redisUtils.get("ete_login_token:" + token + "_" + user.getUserName());
        return userInfo != null ;
    }
}
