package com.eternal.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jiajunmei
 */
@Data
public class UserRegisterVo implements Serializable {

    private String userName;

    private String email;

    private String phone;

    private String nickName;

    private String privateKeyBa;

    private String publicKey;

    private String masterKeyBa;
}
