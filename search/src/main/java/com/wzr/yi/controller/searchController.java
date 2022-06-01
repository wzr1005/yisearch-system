package com.wzr.yi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzr.yi.Mapper.IndexPropertyMapper;
import com.wzr.yi.config.MysqlConfig;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.util.CglibProxy;
import com.wzr.yi.util.DynamicProxyInvocation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import com.wzr.yi.service.IndexService;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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



    @GetMapping("/testdb")
    public String testDb(){
        return indexService.Testmybatis();
    }

    @PostMapping("/insert")
    public void BulkInsertMysql(@RequestParam String FilePath) {
//        DynamicProxyInvocation dyBulkInsertMysql = new DynamicProxyInvocation(
//                indexService);
//        CglibProxy cglibProxy = new CglibProxy(indexService);
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(indexService.getClass());
//        enhancer.setCallback(cglibProxy);
//        IndexService cgIndexService = (IndexService) enhancer.create();
//        JdbcTemplate jdbcTemplate = mysqlConfig.getJdbcTemplate();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(FilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;
        List<IndexProperty> indexPropertyList = new ArrayList<>();
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
                JSONObject obj = (JSONObject) JSON.parse(line);
                IndexProperty indexProperty = new IndexProperty(obj);
                indexPropertyList.add(indexProperty);
            } catch (IOException e) {
                e.printStackTrace();
            }
            break;
        }
        indexService.BulkInsertMysql(indexPropertyList);
    }
}
