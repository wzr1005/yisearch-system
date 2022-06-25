package com.wzr.yi.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/5/30 11:32 下午
 */
@Slf4j
//@Component
public class MysqlUtils {

    public static <T> String generateSqlBatch(List<T> tList){
        StringBuffer stringBuffer = new StringBuffer("INSERT INTO index_property (");

//        String rawSql = ;
//        "name, alias, eid, serial_name, starring, " +
//        "director, gene_type, resource_wap, resource_pc, , resource_rank, " +
//                "year, hot_count, status, describe_info;
        Field[] fields = tList.get(0).getClass().getDeclaredFields();
        for(Field field : fields){
            String filedName = field.getName();
            stringBuffer.append(filedName).append(", ");
        }
        //删除多余逗号
        stringBuffer.deleteCharAt(stringBuffer.length()-1).deleteCharAt(stringBuffer.length()-1);
        stringBuffer.append(") VALUES ");
        // 加入list的属性值
        tList.forEach(ele->{
            if(ele == null){
                return;
            }
            JSONObject obj = (JSONObject) JSONObject.toJSON(ele);
            stringBuffer.append("(");
            for(Field field : fields){
                try {
                    String filedName = field.getName();
                    if (filedName == "id") continue;
                    String value = MyStringUtils.getValueOrDefault(obj.get(filedName));
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

    public  static <T> String generateSqlInsert(T t){

        StringBuffer stringBuffer = new StringBuffer("INSERT INTO yiuser (");
        Field[] fields = t.getClass().getDeclaredFields();
        for(Field field : fields){
            String filedName = field.getName();
            if (filedName.equals("Id") || filedName.equals("createdTime") ) continue;
            stringBuffer.append(filedName).append(", ");
        }
        //删除多余逗号
        stringBuffer.deleteCharAt(stringBuffer.length()-1).deleteCharAt(stringBuffer.length()-1);
        stringBuffer.append(") VALUES ");
        stringBuffer.append("(");

        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(t);
        for(Field field : fields){
            String value = null;
            String filedName = field.getName();
            try {
                if (filedName.equals("Id") || filedName.equals("createdTime") ) continue;

                value = MyStringUtils.getValueOrDefault(jsonObject.get(filedName));
                if (value.contains("'")) {
                    value = value.replace("'", "''");
                }
            }catch (Exception e){
                log.error(filedName + "无法被访问" + e.getMessage());
                value = "";
            }
            stringBuffer.append("'").append(value).append("', ");

        }
        stringBuffer.deleteCharAt(stringBuffer.length()-1).deleteCharAt(stringBuffer.length()-1);
        stringBuffer.append(")");
        return stringBuffer.toString();
    }

}
