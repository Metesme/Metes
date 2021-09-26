package com.eternal.controller;

import com.eternal.common.utils.StringUtils;
import com.eternal.common.web.controller.BaseController;
import com.eternal.domain.UserEntity;
import com.eternal.model.UserInfo;
import com.eternal.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/9/23 4:33 下午
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @ResponseBody
    public String login (@RequestBody UserEntity user){

        if (StringUtils.isNotEmpty( user.getUserName())
                && StringUtils.isNotEmpty( user.getPassword())){
            if(user.getPassword().equals("233")){
                String token = tokenService.createToken(233L ,user.getUserName());
                return token;
            }
        }

        return "false";
    }
}
