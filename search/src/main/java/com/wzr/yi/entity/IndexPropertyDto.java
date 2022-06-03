package com.wzr.yi.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

import static com.wzr.yi.util.MyStringUtils.getValueOrDefault;
import static com.wzr.yi.util.MyStringUtils.listToStringJoin;


/**
 * @autor zhenrenwu
 * @date 2022/6/2 12:53 上午
 */
@Data
public class IndexPropertyDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String eid;

    private String name;

    private String serialName;

    private String starring;

    private String director;

    private String geneType;

    private String alias;

    private String resourceWap;

    private String resourcePc;

    private String resourceRank;

    private String year;

    private String hotCount;

    private String feature;

    private String status;

    private String describeInfo;

    public IndexPropertyDto(IndexProperty indexProperty) {
        this.eid = getValueOrDefault(indexProperty.getEid());
        this.name = getValueOrDefault(indexProperty.getName());
        String serialName = getValueOrDefault(indexProperty.getSerialName());
        this.starring = getValueOrDefault(indexProperty.getStarring());
        this.director =  getValueOrDefault(indexProperty.getStarring());
        this.geneType = getValueOrDefault(indexProperty.getGeneType());
        this.alias = getValueOrDefault(indexProperty.getAlias());
        this.resourceWap = getValueOrDefault(indexProperty.getResourceWap());
        this.resourcePc = getValueOrDefault(indexProperty.getResourcePc());
        this.resourceRank = getValueOrDefault(indexProperty.getResourceRank());
        this.year = String.valueOf(indexProperty.getYear());
        this.hotCount = getValueOrDefault(indexProperty.getHotCount());
        this.feature = getValueOrDefault(indexProperty.getFeature());
        this.status = getValueOrDefault(indexProperty.getStatus());
        this.describeInfo = getValueOrDefault(indexProperty.getDescribeInfo());
    }

}