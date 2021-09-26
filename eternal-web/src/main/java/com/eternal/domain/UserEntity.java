package com.eternal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 4:04 下午
 */
@Data
public class UserEntity {
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String nickName;
}
