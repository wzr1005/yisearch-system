package com.wzr.yi.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wzr.yi.entity.IndexPropertyDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/6/3 11:38 上午
 * 个人String的工具类，支持泛型输入
 * 1. 支持取出泛型的内容并输出为字符串
 * 2. 将list转为String
 * 3. 将List对象转为可以执行的sql语句，方便batch插入。
 * ..etc.
 */
@Slf4j
@Component
public class MyStringUtils {
    public static String getValueOrDefault(Object obj){
        if(obj == null) return "";
        if(obj instanceof String) return (String) obj;
        if(obj instanceof List<?>){
            return listToStringJoin((List<?>) obj);
        }
        if(obj instanceof Integer || obj instanceof Float) return String.valueOf(obj);
        return obj.toString();
    }

    public static String geyKeyWordFromList(List<?> list, String field){
        StringBuffer stringBuffer = new StringBuffer();
        if(list == null) return "";
        for(Object l : list){
            JSONObject jsonObject = (JSONObject) l;
            String value = jsonObject.getString(field);
            stringBuffer.append(value).append(";");
        }
//        list.forEach(l->{
//
//        });
        if(stringBuffer.capacity()!=0) return "";
        stringBuffer.deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }
    public static String listToStringJoin(List<?> l){
        if(l.size() == 0) return "";
        return StringUtils.join(l.toArray(), ";");
    }

    public static String ObjectToString(Object object){
        ObjectMapper mapper = new ObjectMapper();
        String valueJson = "";
        try {
            valueJson = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return valueJson;
    }
    public static String generateSqlBatch(List<IndexPropertyDto> indexPropertyDtoList){
        StringBuffer stringBuffer = new StringBuffer("INSERT INTO index_property (");

//        String rawSql = ;
//        "name, alias, eid, serial_name, starring, " +
//        "director, gene_type, resource_wap, resource_pc, , resource_rank, " +
//                "year, hot_count, status, describe_info;
        Field[] fields = indexPropertyDtoList.get(0).getClass().getDeclaredFields();
        for(Field field : fields){
            String filedName = field.getName();
            stringBuffer.append(filedName).append(", ");
        }
        //删除多余逗号
        stringBuffer.deleteCharAt(stringBuffer.length()-1).deleteCharAt(stringBuffer.length()-1);
        stringBuffer.append(") VALUES ");
        // 加入list的属性值
        indexPropertyDtoList.forEach(ele->{
            if(ele == null){
                return;
            }
            JSONObject obj = (JSONObject) JSONObject.toJSON(ele);
            stringBuffer.append("(");
            for(Field field : fields){
                try {
                    String filedName = field.getName();
                    if (filedName == "id") continue;
                    String value = getValueOrDefault(obj.get(filedName));
                    if (value.length() > 254) {
                        value = value.substring(0, 255);
                    }
                    if (value.contains("'")) {
                        value = value.replace("'", "''");
                    }
                    stringBuffer.append("'").append(value).append("', ");
                }catch (Exception e){
                    log.error(obj.toString());
                }
            }
            // "value, "
            stringBuffer.deleteCharAt(stringBuffer.length()-1).deleteCharAt(stringBuffer.length()-1);
            // "value), "
            stringBuffer.append("), ");
        });
        stringBuffer.deleteCharAt(stringBuffer.length()-1).deleteCharAt(stringBuffer.length()-1);
        return stringBuffer.toString();
    }

}
