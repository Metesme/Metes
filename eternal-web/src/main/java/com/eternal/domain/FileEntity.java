package com.eternal.domain;

import lombok.Data;

import java.math.BigInteger;

/**
 * @author Eternal
 * @version 1.0
 * @description: 文件实体类
 * @date 2021/9/23 4:09 下午
 */
@Data
public class FileEntity {
    private String fileName;
    private BigInteger fileSize;
    private String cid;
}
