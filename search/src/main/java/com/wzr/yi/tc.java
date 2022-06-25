package com.wzr.yi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @autor zhenrenwu
 * @date 2022/6/26 1:39 上午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
@Slf4j
public class tc {
    @GetMapping("/test1")
    public String test(){
        return "hhh";
    }
}
