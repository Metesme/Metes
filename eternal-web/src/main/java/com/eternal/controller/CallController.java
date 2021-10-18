package com.eternal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eternal
 * @version 1.0
 * @description: TODO
 * @date 2021/10/13 11:23 下午
 */

@RestController
@RequestMapping("/call")
public class CallController {
    @GetMapping("/taskStatus")
    public String CallBackStatus(){
        return "";
    }
}
