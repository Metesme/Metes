package com.eternal.service.impl;

import com.eternal.domain.UserEntity;
import com.eternal.mapper.UserMapper;
import com.eternal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/24 2:20 下午
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<UserEntity> selectUserList(UserEntity entity) {
        return null;
    }
}