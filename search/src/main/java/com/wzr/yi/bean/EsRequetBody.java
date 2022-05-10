package com.wzr.yi.bean;

import lombok.Data;

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
    private String fields;
    private String excludes;
    private String json;
}
