package com.eternal.service;

import com.eternal.domain.UserEntity;
import com.eternal.domain.UserKeyEntity;
import com.eternal.vo.UserLoginVo;

import java.util.List;

/**
 * @author jiajunmei
 */
public interface IUserService {
    List<UserEntity> selectUserList(UserEntity entity);

    Boolean checkToken (String token);
    int insertUser(UserEntity entity);
    int insertUserKey(UserKeyEntity entity);
    boolean isUserNameExist(String userName);
    boolean isUserEmailExist(String email);
    boolean isPhoneExist(String phone);
    UserEntity selectUserByUserName (String userName);
    UserKeyEntity selectUserKeyByUserId (Long userId);
    UserLoginVo getUserByToken (String token);


}
