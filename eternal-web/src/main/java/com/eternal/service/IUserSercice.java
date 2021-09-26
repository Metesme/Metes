package com.eternal.service;

import com.eternal.domain.UserEntity;

import java.util.List;

public interface IUserSercice {
    List<UserEntity> selectUserList(UserEntity entity);
}
