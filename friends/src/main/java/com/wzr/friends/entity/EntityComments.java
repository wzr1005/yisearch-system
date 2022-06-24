package com.wzr.friends.entity;

/**
 * 实体详情页的评论
 * @autor zhenrenwu
 * @date 2022/6/24 2:18 上午
 */
public class EntityComments {
    //评论id
    private Integer Id;

    //entity id
    private String eid;

    // 评论内容
    private String contents;

    // 发表时间
    private String createTime;

    private String fatherId;

    // 点赞次数
    private int likeTimes;

}