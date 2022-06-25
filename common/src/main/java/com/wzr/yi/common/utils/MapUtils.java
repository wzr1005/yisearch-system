package com.wzr.yi.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 11:51 下午
 */
public class MapUtils {

    public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException{
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            //值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查
            field.setAccessible(true);
            String key = field.getName();
            Object value = field.get(obj);
            map.put(key, value);
        }
        return map;

    }
}
