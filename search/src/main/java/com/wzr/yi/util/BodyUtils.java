package com.wzr.yi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/18 4:54 上午
 */
public class BodyUtils {

    public static List<Map<String, Object>> recommendBody(List<Map<String, Object>> list){
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
}
