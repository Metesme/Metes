package com.eternal.domain;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;

/**
 * @author Eternal
 * @version 1.0
 * @description: 文件实体类
 * @date 2021/9/23 4:09 下午
 */
@Data
@TableName("file")
public class FileEntity extends BaseEntity{
    @TableField(value="file_name",whereStrategy= FieldStrategy.DEFAULT)
    private String fileName;
    @TableField(value="file_size")
    private Long fileSize;
    @TableField(value="file_path")
    private String  filePath;
    @TableField(value="user_id")
    private Long userId;
    @TableField(value = "cid")
    private String cid;
}
