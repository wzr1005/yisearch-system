package com.wzr.yi.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.util.MyStringUtils;
import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/2/20 4:43 下午
 */
@Data
public class EsRequetBody implements Cloneable {
    private String indexName;
    private String type;
    private String id;
    private String year;
    private String series;
    private String resource;
    private String mapping;
    private String pay;
    private String excludes;
    private String json;
    private String query;
    private String[] fields;
    public static Map<String, Float> BOOSTMAP = new HashMap<>();
    static {
        BOOSTMAP.put("name", 7.0F);
        BOOSTMAP.put("serialName", 5.0F);
        BOOSTMAP.put("starring", 5.0F);
        BOOSTMAP.put("director", 6.0F);
        BOOSTMAP.put("alias", 4.0F);
    }

//    public EsRequetBody deepClone() {
//        Field[] fields = this.getClass().getDeclaredFields();
//        Map<String, Object> mp = new HashMap<>();
//        for(Field field: fields){
//            try {
//                String key = field.getName();
//                Object value = field.get(this);
//                mp.put(key, value);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        String json = JSON.toJSONString(mp);
//        JSONObject jsonObject = JSON.parseObject(json);
//        return (EsRequetBody) jsonObject;
//
//    }

    public EsRequetBody() {
    }

    public EsRequetBody(EsRequetBody esRequetBody) {
        this.indexName = esRequetBody.getIndexName();
        this.type = esRequetBody.getType();
        this.id = esRequetBody.getId();
        this.year = esRequetBody.getYear();
        this.series = esRequetBody.getSeries();
        this.resource = esRequetBody.getResource();
        this.mapping = esRequetBody.getMapping();
        this.pay = esRequetBody.pay;
        this.excludes = esRequetBody.getExcludes();
        this.json = esRequetBody.getJson();
        this.query = esRequetBody.getQuery();
        this.fields = esRequetBody.getFields();
    }
}
