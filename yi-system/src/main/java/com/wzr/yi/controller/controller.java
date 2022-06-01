package com.wzr.yi.controller;

import com.wzr.yi.MainApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @autor zhenrenwu
 * @date 2022/2/21 10:01 下午
 */
@RestController
public class controller {
    @GetMapping("/")
    public String test(){
        return "hello";
    }
}
