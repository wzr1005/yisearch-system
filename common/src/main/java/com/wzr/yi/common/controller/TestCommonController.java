package com.wzr.yi.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 9:37 下午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
@Slf4j
public class TestCommonController {

    @GetMapping("/test")
    public String register(){
        return "hello, common";
    }
}
