package com.eternal.service;

import com.eternal.domain.FileEntity;

import java.io.File;
import java.util.List;

public interface IFileService {
    int insert(FileEntity entity);
    List<FileEntity> selectList(FileEntity entity);
    void pin (FileEntity entity) ;
}
