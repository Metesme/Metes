package com.eternal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.eternal.domain.UserEntity;
import com.eternal.domain.UserKeyEntity;
import com.eternal.mapper.UserKeyMapper;
import com.eternal.mapper.UserMapper;
import com.eternal.vo.UserLoginVo;
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

    @Resource
    private UserKeyMapper userKeyMapper;

    @Override
    public List<UserEntity> selectUserList(UserEntity entity) {
        return null;
    }

    @Override
    public int insertUser(UserEntity entity) {
        return userMapper.insert(entity);
    }
    @Override
    public int insertUserKey(UserKeyEntity entity) {
        return userKeyMapper.insert(entity);
    }
    @Override
    public boolean isUserNameExist(String userName){
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEntity::getUserName, userName);
        List<UserEntity> list = userMapper.selectList(lambdaQueryWrapper);
        return list != null && !list.isEmpty();
    }

    @Override
    public boolean isUserEmailExist(String email) {
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEntity::getEmail, email);
        UserEntity entity = userMapper.selectOne(lambdaQueryWrapper);
        return entity != null;
    }

    @Override
    public boolean isPhoneExist(String phone) {
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEntity::getPhone, phone);
        UserEntity entity = userMapper.selectOne(lambdaQueryWrapper);
        return entity != null;
    }

    @Override
    public UserEntity selectUserByUserName(String userName) {
        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEntity::getUserName, userName);
        return userMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public UserKeyEntity selectUserKeyByUserId(Long userId) {
        LambdaQueryWrapper<UserKeyEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserKeyEntity::getId, userId);
        return userKeyMapper.selectOne(lambdaQueryWrapper);
    }
@Override
    public UserLoginVo getUserByToken(String token) {
        UserLoginVo userInfo;
        token = token.replace("Bearer", "").trim();
        userInfo = (UserLoginVo)redisUtils.get("ete_login_token:" + token );
        return userInfo;
    }


    @Override
    public Boolean checkToken(String token) {
        UserLoginVo userInfo;
        userInfo = (UserLoginVo)redisUtils.get("ete_login_token:" + token );
        return userInfo != null ;
    }

}
