package com.wzr.yi.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @autor zhenrenwu
 * @date 2022/2/24 12:41 上午
 */
@Data
public class FilmItems implements Serializable {
//    TABLE：使用一个特定的数据库表格来保存主键。
//    SEQUENCE：根据底层数据库的序列来生成主键，条件是数据库支持序列。
//    IDENTITY：主键由数据库自动生成（主要是自动增长型）
//    AUTO：主键由程序控制。
    private int id;

    //@Column(name = "key")
    private String key;
    //@Column(name="display")
    private String display;
    //@Column(name="name")
    private String name;
    //@Column(name="synonym")
    private String synonym;
    //@Column(name="title")
    private String title;
    //@Column(name="url")
    private String url;
    //@Column(name="poster")
    private String poster;
    //@Column(name="posterhengtu")
    private String posterhengtu;
    //@Column(name="language")
    private String language;
    //@Column(name="summary")
    private String summary;
    //@Column(name="summaryurl")
    private String summaryurl;
    //@Column(name="douban")
    private String douban;
    //@Column(name="type")
    private String type;
    //@Column(name="actor")
    private String actor;
    //@Column(name="director")
    private String director;
    //@Column(name="name_prob")
    private String name_prob;
    //@Column(name="serial_prob")
    private String serial_prob;
    //@Column(name="year")
    private String year;
    //@Column(name="year1")
    private String year1;
    //@Column(name="area")
    private String area;
    //@Column(name="season")
    private String season;
    //@Column(name="seasonname")
    private String seasonname;
    //@Column(name="xiliename")
    private String xiliename;
    //@Column(name="resourcelist")
    private String resourcelist;
    //@Column(name="shangyingtime")
    private String shangyingtime;
    //@Column(name="qingxidu")
    private String qingxidu;
    //@Column(name="playtime")
    private String playtime;
    //@Column(name="tag")
    private String tag;
    //@Column(name="date")
    private String date;
    //@Column(name="redu")
    private String redu;
    //@Column(name="playshow")
    private String playshow;
    //@Column(name="playshow_count")
    private String playshow_count;
    //@Column(name="ext_query")
    private String ext_query; //添加扩展词
    //@Column(name="dockey")
    private String dockey;



}
