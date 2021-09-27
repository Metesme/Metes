package com.eternal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ete
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 4:04 下午
 */
@Data
public class UserEntity {
    private String userName;
    private String password;
    private Long userId;
    private String privateKeyBa;
    private String publicKey;
    private String masterKey;
}
