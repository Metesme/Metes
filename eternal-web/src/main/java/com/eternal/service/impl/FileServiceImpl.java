package com.eternal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.eternal.domain.FileEntity;
import com.eternal.domain.UserKeyEntity;
import com.eternal.mapper.FileMapper;
import com.eternal.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.LambdaMetafactory;
import java.util.List;

/**
 * @author Hikari
 * @version 1.0
 * @description: TODO
 * @date 2021/9/29 11:55 上午
 */
@Service
public class FileServiceImpl implements IFileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public int insert(FileEntity entity) {
        return fileMapper.insert(entity);
    }

    @Override
    public List<FileEntity> selectList(FileEntity entity) {
        return new LambdaQueryChainWrapper<>(fileMapper)
                .eq(FileEntity::getUserId, entity.getUserId())
                .eq(FileEntity::getFileName, entity.getFileName())
                .orderByDesc(FileEntity::getCreateTime)
                .list();
    }
}
