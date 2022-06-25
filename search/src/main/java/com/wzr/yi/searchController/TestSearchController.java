package com.wzr.yi.searchController;

import com.wzr.yi.service.SearchPrepareService;
import com.wzr.yi.bean.BeanUtils;
import com.wzr.yi.service.IndexService;
import com.wzr.yi.service.SearchPostService;
import com.wzr.yi.common.utils.ElasticSearchUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/22 11:43 上午
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
@Slf4j
public class TestSearchController {

    private final IndexService indexService;
    private final ElasticSearchUtil elasticSearchUtil;
    private final SearchPrepareService searchPrepareService;
    private final SearchPostService searchPostService;
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


    @GetMapping("/testService")
    public List<Map<String, Object>> getOtherService(){
        return null;
    }
}
