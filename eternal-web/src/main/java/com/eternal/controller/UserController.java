package com.eternal.controller;

import com.eternal.common.utils.StringUtils;
import com.eternal.common.web.controller.BaseController;
import com.eternal.common.web.domain.AjaxResult;
import com.eternal.domain.UserEntity;
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
        //测试数据

        String masterKeyBa = "lw0Up7IcOyTbeB7RWcZk/tihBTPtXlswocqS3IgKYXmH5FxzQO6zuzJLQbrEoDx6";

        String privateKeyBa = "PXC2NeW5vMRA9kKHlEsMNVZm9uQQcD3dDGRwl4+brYbmi1YHi9NfsXj6kSjNnmeDlWw/w5+FYZvN5nJPRHpYrYSJNr9yaS25kdbR5Ac61cKgmsMSg5mgIMPj3UHUpiN+mde/h1W+sXGhf7RjkakqTqTtYFrroaa1vSqBWvq09qVEceLFy2j4jD7G98lFSHCH87yN8CDiCooaEyVdkzSt9Os0NerUcIw+ndzFiNPr1Bhe26KQiZQz+PVSdBzpin/9NtfzA6pAUeDJGIJsv+FZihKs8oPtrUZ9dFlRsrfwzlZ7vPTOt4quY5/5myjPhdXbNJsyYNgfubqDTL6TsRlmNvMTc9kc0nbiZMczgwBpwq9rnJT4Pe7u9V5gwRh9S5lkqCBclqKWRGhWnteHz9IKMt+ZomO/dTva/XIa4DtAkU9gjq+nmXZop4o5UkkzdyvMtnjjhny+MMr0/9iKDLQg2mI37oe/ul8eufsLzDLlnZOhv1AYoT8b+zJ9NA0WSsfmgF1PgiwNVWU+PDoiNaUtkvC0IgC7iPNYGenYXR10aZyrGYFDKqMSHi/ES9igVh8mWeOZFiKEwllppSBGTTLlkFmdzI1x1BhKz8pPZ9AUlEYt0HNJIjEMNDvMiXBEGXyrS//IwcrBemphHoi5wcjJ4A==";

        String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALJs6GgyhOet18e58e1jx0ubqVZx3nVr\n" +
                "oPVecwODr3QKTWD5hTIidMhLIv/8rqRs9bEc1K99ZzLhojiyG/fYYVcCAwEAAQ==";

        AjaxResult result = new AjaxResult();
        if (StringUtils.isNotEmpty( user.getUserName())
                && StringUtils.isNotEmpty( user.getPassword())){
            if(user.getPassword().equals("chenjikang000000")){
                String token = tokenService.createToken(233L ,user.getUserName());
                System.out.println(token);
                //加密 token 发送到客户端解密
                String encrypt = RSAUtils.encrypt(token, publicKey);
                result.put("token",encrypt);
                result.put("masterKeyBa",masterKeyBa);
                result.put("privateKeyBa",privateKeyBa);
                result.put("status","success");
                return result;
            }
        }
        result.put("msg","Sorry ,the account does not exist" );
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
