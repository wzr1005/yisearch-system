package com.wzr.yi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @autor zhenrenwu
 * @date 2022/6/25 8:15 下午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
@Slf4j
public class friendController {

    @GetMapping("/testFriends")
    public String testFriends(){
        return "hello, i'm friends";
    }
}
