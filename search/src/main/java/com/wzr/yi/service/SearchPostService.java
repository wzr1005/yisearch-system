package com.wzr.yi.service;

import com.wzr.yi.common.searchEntity.EsRequestBody;

import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/10 4:04 下午
 * 搜索的后置处理
 */
public interface SearchPostService {
    /**
     * 对一次搜索之后的结果集进行排序
     * @param list
     * @param esRequestBody
     * @return
     */
    public List<Map<String, Object>> resultSort(List<Map<String, Object>> list, EsRequestBody esRequestBody);
}
