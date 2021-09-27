package com.eternal.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 4:04 下午
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_key")
public class UserKeyEntity extends BaseEntity {

    @TableField(value="id")
    private Long id;
    @TableField(value="privateKeyBa")
    private String privateKeyBa;
    @TableField(value="publicKey")
    private String publicKey;
    @TableField(value="masterKeyBa")
    private String masterKeyBa;
}
