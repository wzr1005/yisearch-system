package com.wzr.yi.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/2 10:47 下午
 */
@RequiredArgsConstructor
@Slf4j
@RestController
public class Test2 {
    private final SearchDataSource myDataSource;

    private final DruidPool druidPool;
    @SneakyThrows
    @RequestMapping("/testDruid1")
    public void testDruid(){
        log.info("建立一个连接。。。");
        System.out.println(druidPool);
        List<Map<String, Object>> resultList = druidPool.executeSqlQuery("select * from index_property limit 1");
        System.out.println(resultList);
    }

}
