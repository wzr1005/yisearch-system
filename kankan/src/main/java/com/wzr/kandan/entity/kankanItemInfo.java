package com.wzr.kandan.entity;

import lombok.Data;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 11:45 上午
 */
@Data
public class kankanItemInfo {
    private Integer id;
    private String name;
    private String eid;
    private String year;
    // 评分
    private String score;
    // 加入看单的次数
    private String addTime;

    // item类型
    private String geneType;
    // 演职员表
    private String starring;
    private String director;
    // 海报
    private String poster;
}
