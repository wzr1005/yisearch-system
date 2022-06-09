package com.wzr.yi.bean;

import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/2/20 4:43 下午
 */
@Data
public class EsRequetBody {
    private String indexName;
    private String type;
    private String id;
    private String mapping;
    private String excludes;
    private String json;
    private String query;
    private String[] fields;
}
