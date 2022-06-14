package com.wzr.yi.controller;

import com.wzr.yi.bean.BeanUtils;
import com.wzr.yi.bean.EsRequetBody;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.service.SearchPrepareService;
import com.wzr.yi.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.wzr.yi.service.IndexService;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import static com.wzr.yi.Constant.Constant.*;
import static com.wzr.yi.util.MyStringUtils.ObjectToString;
import static com.wzr.yi.util.matchSortUtils.getEditDistance;

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
    private final SearchPrepareService searchPrepareService;
    private final BeanUtils beanUtils;
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
        List<Map<String, Object>> resultList = new ArrayList<>();
        // 对query进行分析、拆解
        List<EsRequetBody> esRequetBodyList = searchPrepareService.QUAnalysis(requestBody);
        // 将QU过后的query进行查询，获得结果
        esRequetBodyList.forEach(
                requetBody -> {
                    // 对查询对结果进行排序
                    List<Map<String, Object>> result = elasticSearchUtil.queryEs(requetBody);
                    resultList.addAll(result);
                }
        );
        resultList.addAll(elasticSearchUtil.queryEs(requestBody));
        return resultList;
    }

    @DeleteMapping("/deletedoc")
    public void DeleteDoc(String index, String type, String id){
        elasticSearchUtil.deleteDataById(index, type, id);
    }


}
