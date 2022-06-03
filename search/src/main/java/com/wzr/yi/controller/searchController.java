package com.wzr.yi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzr.yi.Mapper.IndexPropertyMapper;
import com.wzr.yi.config.ElasticsearchConfig;
import com.wzr.yi.config.MysqlConfig;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.util.CglibProxy;
import com.wzr.yi.util.DynamicProxyInvocation;
import com.wzr.yi.util.LoadTextByLineMulti;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.wzr.yi.service.IndexService;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/2/21 10:01 下午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class searchController {

    private final IndexService indexService;

    private final IndexPropertyMapper indexPropertyMapper;
    private final MysqlConfig mysqlConfig;
    @GetMapping("/search")
    public String test(){
        return "search";
    }



    @GetMapping("/testMybatis")
    public String testDb(){
        return indexService.Testmybatis();
    }

    @GetMapping("/testDruid")
    public List<Map<String, Object>> testDruid(){
        return indexService.testDruid();
    }


    @PostMapping("/insert")
    public void BulkInsertMysql(@RequestParam String FilePath) {
        LoadTextByLineMulti loadTextByLineMulti = new LoadTextByLineMulti(FilePath);
        indexService.BulkInsertMysql(loadTextByLineMulti.loadLineDataMulti(IndexPropertyDto.class));
    }

}
