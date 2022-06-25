package com.wzr.yi.rest;

import com.alibaba.druid.pool.DruidDataSource;
import com.wzr.yi.common.config.DruidPool;
import com.wzr.yi.common.utils.ElasticSearchUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @autor zhenrenwu
 * @date 2022/6/25 8:18 下午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dataprocess")
@Slf4j
public class TestDataController {
    private final DruidPool druidPool;
    private final ElasticSearchUtil elasticSearchUtil;
    @GetMapping("/testdataprocess")
    public String testFriends(){
        return "hello, i'm dataprocess";
    }
}
