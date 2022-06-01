package com.wzr.yi.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @autor zhenrenwu
 * @date 2022/6/1 2:28 上午
 */
public class GetOrDefault {
    public static int getInteger(JSONObject obj, String key){
        Object o = obj.get(key);
        return o == null ? 0 : (int) obj.get(key);
    }
    public static String getString(JSONObject obj, String key){
        Object o = obj.get(key);
        return o == null ? "" : (String)obj.get(key);
    }
}
