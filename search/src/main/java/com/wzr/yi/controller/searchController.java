package com.wzr.yi.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.util.CglibProxy;
import com.wzr.yi.util.DynamicProxyInvocation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
public class searchController {

    private final IndexService indexService;

    @GetMapping("/search")
    public String test(){
        return "search";
    }



    @GetMapping("/testdb")
    public String testDb(){
        return null;
    }

    @PostMapping("/insert")
    public void BulkInsertMysql(@RequestParam String FilePath) {
        DynamicProxyInvocation dyBulkInsertMysql = new DynamicProxyInvocation(
                indexService);
        IndexService cgIndexService = (IndexService) new CglibProxy(indexService).createProxyedObj(indexService.getClass());

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
                indexPropertyList.add(new IndexProperty(obj));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        cgIndexService.BulkInsertMysql(indexPropertyList);
    }
}
