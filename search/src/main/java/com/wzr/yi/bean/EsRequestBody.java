package com.wzr.yi.bean;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/2/20 4:43 下午
 */
@Data
public class EsRequestBody implements Cloneable {
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
    private String category;
    private String query;
    private String playLink;
    private String[] fields;
    public static Map<String, Float> BOOSTMAP = new HashMap<>();
    static {
        BOOSTMAP.put("name", 7.0F);
        BOOSTMAP.put("serialName", 5.0F);
        BOOSTMAP.put("starring", 5.0F);
        BOOSTMAP.put("director", 6.0F);
        BOOSTMAP.put("alias", 4.0F);
    }

//    public EsrequestBody deepClone() {
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
//        return (EsrequestBody) jsonObject;
//
//    }

    public EsRequestBody() {
        this.fields = new String[]{"name", "describeInfo", "starring"};
    }

    public EsRequestBody(EsRequestBody esRequestBody) {
        this.indexName = esRequestBody.getIndexName();
        this.type = esRequestBody.getType();
        this.id = esRequestBody.getId();
        this.year = esRequestBody.getYear();
        this.series = esRequestBody.getSeries();
        this.resource = esRequestBody.getResource();
        this.mapping = esRequestBody.getMapping();
        this.pay = esRequestBody.pay;
        this.excludes = esRequestBody.getExcludes();
        this.json = esRequestBody.getJson();
        this.query = esRequestBody.getQuery();
        this.fields = esRequestBody.getFields();
    }
}
