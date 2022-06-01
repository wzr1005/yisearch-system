package com.wzr.yi.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

import static com.wzr.yi.service.impl.indexServiceImpl.listToStringJoin;

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
        this.eid = indexProperty.getEid();
        this.name = indexProperty.getName();
        this.serialName = indexProperty.getSerialName();
        this.starring = listToStringJoin(indexProperty.getStarring());
        this.director = listToStringJoin(indexProperty.getStarring());
        this.geneType = listToStringJoin(indexProperty.getGeneType());
        this.alias = listToStringJoin(indexProperty.getAlias());
        this.resourceWap = listToStringJoin(indexProperty.getResourceWap());
        this.resourcePc = listToStringJoin(indexProperty.getResourcePc());
        this.resourceRank = String.valueOf(indexProperty.getResourceRank());
        this.year = String.valueOf(indexProperty.getYear());
        this.hotCount = String.valueOf(indexProperty.getHotCount());
        this.feature = indexProperty.getFeature();
        this.status = indexProperty.getStatus();
        this.describeInfo = indexProperty.getDescribeInfo();
    }
}
