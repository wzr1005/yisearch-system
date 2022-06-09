package com.wzr.yi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzr.yi.Mapper.IndexPropertyMapper;
import com.wzr.yi.bean.EsRequetBody;
import com.wzr.yi.config.ElasticsearchConfig;
import com.wzr.yi.config.MysqlConfig;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.util.*;
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
import java.util.concurrent.ThreadPoolExecutor;

import static com.wzr.yi.util.MyStringUtils.ObjectToString;

/**
 * @autor zhenrenwu
 * @date 2022/2/21 10:01 下午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class searchController {

    private final IndexService indexService;
    private final ElasticSearchUtil elasticSearchUtil;

    @GetMapping("/hello")
    public String test(){
        return "hello";
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
    @GetMapping("/esBulk")
    public void BulkInsertEs(String index, String type){
        String path = "/Users/zhenrenwu/Documents/txwd/film_entity.txt";
        List<String> list = new LoadTextByLineMulti().loadLineStringList(path);
        List<String> toInsertList = new ArrayList<>();
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.getThreadPoolNoReject();
        //将读取的list转换为List<Object>
        list.forEach(entity->{
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    IndexPropertyDto indexPropertyDto = new IndexPropertyDto(new IndexProperty(entity));
                    String valueJson = ObjectToString(indexPropertyDto);
                    toInsertList.add(valueJson);
                }
            });
        });

        ThreadPoolUtils.isCompleted(threadPoolExecutor);
        elasticSearchUtil.BulkInsertDocument(index, type, toInsertList);
    }

    @PostMapping("/testes")
    public List<Map<String, Object>> TestEs(@RequestBody EsRequetBody requestBody){
        System.out.println(requestBody);
        return elasticSearchUtil.queryEs(requestBody);
    }

    @DeleteMapping("/deletedoc")
    public void DeleteDoc(String index, String type, String id){
        elasticSearchUtil.deleteDataById(index, type, id);
    }
}
