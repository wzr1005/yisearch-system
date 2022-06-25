package com.wzr.yi.entity;

import lombok.Data;

import java.util.Map;

import static com.wzr.yi.common.utils.MyStringUtils.getValueOrDefault;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 11:45 上午
 */
@Data
public class KankanItemInfo {

    // 因为经常修改，所以存放在redis里面。

    private String name;
    private String eid;
    private String year;
    // 评分
    private int evaluateScore;
    // 加入看单的次数
    private String addTimes;

    // item类型
    private String geneType;
    // 演职员表
    private String starring;
    private String director;
    // 海报
    private String poster;

    public KankanItemInfo(Map<String, Object> kandanMp) {
        this.name = getValueOrDefault(kandanMp.get("name"));
        this.eid = getValueOrDefault(kandanMp.get("eid"));
        this.year = getValueOrDefault(kandanMp.get("year"));
        this.evaluateScore = Integer.parseInt(getValueOrDefault(kandanMp.get("evaluateScore")));
        this.addTimes = getValueOrDefault(kandanMp.get("addTimes"));
        this.geneType = getValueOrDefault(kandanMp.get("geneType"));;
        this.starring = getValueOrDefault(kandanMp.get("starring"));;
        this.director = getValueOrDefault(kandanMp.get("director"));;
        this.poster = getValueOrDefault(kandanMp.get("poster"));;
    }
}
