package com.wzr.yi.entity;

import lombok.Data;

import java.util.List;

import static com.wzr.yi.util.MyStringUtils.getValueOrDefault;
import static com.wzr.yi.util.MyStringUtils.geyKeyWordFromList;


/**
 * @autor zhenrenwu
 * @date 2022/6/2 12:53 上午
 */
@Data
public class IndexPropertyDto {
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

    private String playLink;
    private String poster;

    public IndexPropertyDto(IndexProperty indexProperty) {
        this.eid = getValueOrDefault(indexProperty.getEid());
        this.id = this.eid;
        this.name = getValueOrDefault(indexProperty.getName());
        this.serialName = getValueOrDefault(indexProperty.getSerialName());
        this.starring = getValueOrDefault(indexProperty.getStarring());
        this.director =  getValueOrDefault(indexProperty.getStarring());
        this.geneType = getValueOrDefault(indexProperty.getGeneType());
        this.alias = getValueOrDefault(indexProperty.getAlias());
        List<?> resourceWapList = indexProperty.getResourceWap();
//        System.out.println(resourceWapList.get(0));
        this.resourceWap = geyKeyWordFromList(resourceWapList, "source");

        this.resourcePc = geyKeyWordFromList(indexProperty.getResourcePc(), "source");
        this.resourceRank = getValueOrDefault(indexProperty.getResourceRank());
        this.year = String.valueOf(indexProperty.getYear());
        this.hotCount = getValueOrDefault(indexProperty.getHotCount());
        this.feature = getValueOrDefault(indexProperty.getFeature());
        this.status = getValueOrDefault(indexProperty.getStatus());
        this.describeInfo = getValueOrDefault(indexProperty.getDescribeInfo());
        this.playLink = indexProperty.getPlayLink();
        this.poster = indexProperty.getPoster();
    }

    @Override
    public String toString() {
        return "{" +
                "id: '" + id + '\'' +
                ", eid: '" + eid + '\'' +
                ", name: '" + name + '\'' +
                ", serialName: '" + serialName + '\'' +
                ", starring: '" + starring + '\'' +
                ", director: '" + director + '\'' +
                ", geneType: '" + geneType + '\'' +
                ", alias: '" + alias + '\'' +
                ", resourceWap: '" + resourceWap + '\'' +
                ", resourcePc: '" + resourcePc + '\'' +
                ", resourceRank: '" + resourceRank + '\'' +
                ", year: '" + year + '\'' +
                ", hotCount: '" + hotCount + '\'' +
                ", feature: '" + feature + '\'' +
                ", status: '" + status + '\'' +
                ", describeInfo: '" + describeInfo + '\'' +
                '}';
    }
}
