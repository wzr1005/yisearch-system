package com.wzr.yi.controller;

import com.wzr.yi.bean.BeanUtils;
import com.wzr.yi.bean.EsRequestBody;
import com.wzr.yi.service.IndexService;
import com.wzr.yi.service.SearchPostService;
import com.wzr.yi.service.SearchPrepareService;
import com.wzr.yi.util.ElasticSearchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/17 4:18 下午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class homeController {

    private final IndexService indexService;
    private final ElasticSearchUtil elasticSearchUtil;
    private final SearchPrepareService searchPrepareService;
    private final SearchPostService searchPostService;
    private final BeanUtils beanUtils;

    @GetMapping("/recommend")
    public List<Map<String, Object>> getRecommend(){
        EsRequestBody esRequestBody = new EsRequestBody();
        esRequestBody.setIndexName("film_index");
        esRequestBody.setType("_doc");
        List<Map<String, Object>> list = elasticSearchUtil.queryHot(esRequestBody);
        List<Map<String, Object>> resList = new ArrayList<>();
        list.forEach(response->{
            Map<String, Object> resMap = new HashMap<>();
            resMap.put("name", response.get("name"));
            if(response.get("poster").equals("") || response.get("playLink").equals("")) return;
            resMap.put("verticalPoster", response.get("poster"));
            resMap.put("year", response.get("year"));
            resMap.put("starring", response.get("starring"));
            resMap.put("director", response.get("director"));
            resMap.put("describeInfo", response.get("describeInfo"));
            resMap.put("playLink", response.get("playLink"));
            resMap.put("hotCount", response.get("hotCount"));
            resList.add(resMap);
        });

        return resList;
    }

    @GetMapping("/detail")
    public Map<String, Object> getEntityDetail(@RequestParam String eid){
        //前端携带eid 去访问全量数据
        EsRequestBody requestBody = new EsRequestBody();
        requestBody.setIndexName("film_index");
        requestBody.setType("_doc");
        requestBody.setId(eid);
        List<Map<String, Object>> resList = elasticSearchUtil.searchDataById(requestBody);
        return resList.isEmpty() ? new HashMap<>() : resList.get(0);
    }

    @GetMapping("/rankList")
    public List<Map<String, Object>> getRankList(@RequestBody @Nullable String type){
        if(type == null) type = "film";
        EsRequestBody requestBody = new EsRequestBody();
        requestBody.setIndexName("bangdan");
        requestBody.setType("_doc");
        requestBody.setCategory(type);
        List<Map<String, Object>> res = elasticSearchUtil.queryBangdan(requestBody);
        return res;
    }

    @GetMapping("/suggestQuery")
    public List<Map<String, Object>> getSuggestQueryt(){
        Map<String, Object> resMap = new HashMap<>();
        List<Map<String, Object>> resList = new ArrayList<>();
        resMap.put("大海道之魅影狂沙", "跳转搜索该词的页面");
        resMap.put("长津湖", "跳转搜索该词的页面");
        resList.add(resMap);
        return resList;
    }
/*
192.168.0.103:8888/home/recommend # 获取首页推荐，滚动，无须传参数，返回的结果有
192.168.0.103:8888/home/detail      # 拿到indexName，type， 需要拿到eid去请求
192.168.0.103:8888/home/rankList    # 排行榜，参数string，可输入film，teleplay,tvshow,cartoon,可为空
192.168.0.103:8888/home/suggestQuery # 获取搜索推荐词，无须参数
192.168.0.103:8888/search/keywordSearch #
192.168.0.103:8888/home/detail
192.168.0.103:8888/home/detail

 */
}
