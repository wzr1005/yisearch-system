package com.wzr.yi.util;

import com.alibaba.fastjson.JSONObject;
import com.wzr.yi.common.utils.MyStringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 6:31 下午
 */
public class parseUtil {
    public static <T> List<Map<String, Object>> processResponse(SearchResponse searchResponse, Class<T> clazz){
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            JSONObject jsonObject = (JSONObject) JSONObject.parse(sourceAsString);
            Field[] fields = clazz.getDeclaredFields();
            Map<String, Object> mp = new HashMap<>();
            for(Field field: fields){
                try {
                    String key = field.getName();
                    String value = MyStringUtils.getValueOrDefault(jsonObject.get(key));
                    mp.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            list.add(mp);
        }

        return list;
    }
}
