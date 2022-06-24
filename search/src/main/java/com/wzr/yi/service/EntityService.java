package com.wzr.yi.service;

import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 12:28 下午
 */
public interface EntityService {

    public List<Map<String, Object>> getDetailEntity();

    public List<Map<String, Object>> getRecommendList();
}
