package com.wzr.yi.searchController;

import com.wzr.yi.bean.BeanUtils;
import com.wzr.yi.common.searchEntity.EsRequestBody;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.service.IndexService;
import com.wzr.yi.service.SearchPostService;
import com.wzr.yi.service.SearchPrepareService;
import com.wzr.yi.common.utils.ElasticSearchUtil;
import com.wzr.yi.util.LoadTextByLineMulti;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @autor zhenrenwu
 * @date 2022/2/21 10:01 下午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
public class searchController {

    private final IndexService indexService;
    private final ElasticSearchUtil elasticSearchUtil;
    private final SearchPrepareService searchPrepareService;
    private final SearchPostService searchPostService;
    private final BeanUtils beanUtils;


    @PostMapping("/insert")
    public void BulkInsertMysql(@RequestParam String FilePath) {
        LoadTextByLineMulti loadTextByLineMulti = new LoadTextByLineMulti(FilePath);
        indexService.BulkInsertMysql(loadTextByLineMulti.loadLineDataMulti(IndexPropertyDto.class));
    }



    @PostMapping("/keywordSearch")
    public List<Map<String, Object>> TestEs(@RequestBody EsRequestBody requestBody){
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1", "1");
        System.out.println(requestBody);
        List<Map<String, Object>> resultList = new ArrayList<>();
        // 对query进行分析、拆解
        List<EsRequestBody> esRequestBodyList = searchPrepareService.QUAnalysis(requestBody);
        // 将QU过后的query进行查询，获得结果
//        esrequestBodyList.forEach(
//                requestBody -> {
//                    // 对查询对结果进行排序
//                    List<Map<String, Object>> result = elasticSearchUtil.queryEs(requestBody);
//                    result = searchPostService.resultSort(result, requestBody);
//                    resultList.addAll(result);
//                }
//        );
        resultList.addAll(elasticSearchUtil.queryEs(requestBody));
        return resultList;
    }

    @DeleteMapping("/deletedoc")
    public void DeleteDoc(String index, String type, String id){
        elasticSearchUtil.deleteDataById(index, type, id);
    }

}
