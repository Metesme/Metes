package com.eternal.controller;

import com.eternal.common.utils.StringUtils;
import com.eternal.common.web.controller.BaseController;
import com.eternal.common.web.domain.AjaxResult;
import com.eternal.domain.UserEntity;
import com.eternal.domain.UserKeyEntity;
import com.eternal.service.IUserService;
import com.eternal.service.TokenService;
import com.eternal.utils.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    @Autowired
    private IUserService userService;

    @PostMapping("/getToken")
    @ResponseBody
    public AjaxResult login (@RequestBody UserEntity user) throws Exception {
        AjaxResult result = new AjaxResult();
        String userName = user.getUserName();
        if (    StringUtils.isNotEmpty( userName)
                && StringUtils.isNotEmpty( userName)
                && userService.isUserNameExist(userName)){

                UserEntity userEntity = userService.selectUserByUserName(userName);
                Long userId = userEntity.getUserId();

            UserKeyEntity userKeyEntity = userService.selectUserKeyByUserId(userId);

            String token = tokenService.createToken(userName);
                System.out.println(token);
                //加密 token 发送到客户端解密
                String encrypt = RSAUtils.encrypt(token, userKeyEntity.getPublicKey());
                result.put("userId",userId);
                result.put("token",encrypt);
                result.put("masterKeyBa",userKeyEntity.getMasterKeyBa());
                result.put("privateKeyBa",userKeyEntity.getPrivateKeyBa());
                result.put("status","success");
                return result;
        }
        result.put("status","error");
        return result;
    }

    @PostMapping("/check")
    @ResponseBody
    public AjaxResult checkToken (@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@RequestBody UserEntity user){
        AjaxResult result = new AjaxResult();
        token = token.replace("Bearer","").trim();
        Boolean aBoolean = userService.checkToken(token, user);
        result.put("loginStatus",aBoolean);
        return result;
    }
}
