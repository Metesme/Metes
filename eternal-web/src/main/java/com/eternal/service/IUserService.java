package com.eternal.service;

import com.eternal.domain.UserEntity;
import com.eternal.domain.UserKeyEntity;

import java.util.List;

public interface IUserService {
    List<UserEntity> selectUserList(UserEntity entity);

    Boolean checkToken (String token);
    int insertUser(UserEntity entity);
    boolean isUserNameExist(String userName);
    boolean isPhoneExist(String phone);
    UserEntity selectUserByUserName (String userName);
    UserKeyEntity selectUserKeyByUserId (Long userId);


}
