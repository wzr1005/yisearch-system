package com.wzr.yi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @autor zhenrenwu
 * @date 2022/2/21 10:01 下午
 */
@RestController
@RequestMapping("/system")
public class controller {
    @GetMapping("/hello")
    public String test(){
        return "hello";
    }
}
