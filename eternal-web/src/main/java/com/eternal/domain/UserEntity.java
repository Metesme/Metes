package com.eternal.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 4:04 下午
 */
@Data
@TableName("user")
public class UserEntity extends BaseEntity {
    @TableField(value="user_name")
    private String userName;
    @TableField(value="password")
    private String password;
    @TableField(value="email")
    private String email;
    @TableField(value="phone")
    private String phone;
    @TableField(value="nick_name")
    private String nickName;
    private Long userId;
    private String privateKeyBa;
    private String publicKey;
    private String masterKey;
}
