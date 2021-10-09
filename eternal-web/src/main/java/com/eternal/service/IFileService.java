package com.eternal.service;

import com.eternal.domain.FileEntity;

import java.io.File;
import java.util.List;

public interface IFileService {
    int insert(FileEntity entity);
    List<FileEntity> selectList(FileEntity entity);
    String pin (FileEntity entity);
}
