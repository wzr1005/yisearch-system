package com.wzr.friends.entity;

import lombok.Data;

import java.util.Set;

/**
 * 好友关系表
 * @autor zhenrenwu
 * @date 2022/2/19 7:40 下午
 */
@Data
public class YiFriendsRelation {

    //好友列表,每个ID存着该id的用户的 [好友列表，好友头像，好友个性签名，好友最新一条聊天记录，
    //参考微博，使用redis来存储，hash结构

    /*
     [userFromId] -> {
        "toId": "addTime"
        ...
     }
     */
    private String userIdFrom;
    private String userIdTo;
    private String createdTime;

    //评论系统


}
