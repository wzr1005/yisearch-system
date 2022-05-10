package com.wzr.yi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wzr.yi.service.IndexService;
/**
 * @autor zhenrenwu
 * @date 2022/2/21 10:01 下午
 */
@RestController
@RequiredArgsConstructor
public class searchController {

    private final IndexService indexService;

    @GetMapping("/search")
    public String test(){
        return "search";
    }

    @GetMapping("/testdb")
    public String testDb(){
        return
    }
}
