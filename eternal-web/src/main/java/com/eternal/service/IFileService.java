package com.eternal.service;

import com.eternal.domain.FileEntity;

import java.util.List;


/**
 * @author jiajunmei
 */
public interface IFileService {
    int insert(FileEntity entity);
    List<FileEntity> selectList(FileEntity entity);
    void pin (FileEntity entity) ;
    boolean deleteFile(FileEntity entity);
}
