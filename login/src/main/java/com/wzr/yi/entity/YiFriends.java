package com.wzr.yi.entity;

import lombok.Data;

import java.util.Set;

/**
 * @autor zhenrenwu
 * @date 2022/2/19 7:40 下午
 */
@Data
public class YiFriends {

    //好友列表,每个ID存着该id的用户的 [好友列表，好友头像，好友个性签名，好友最新一条聊天记录，
    private Integer Id;
//    private String userId;
//    private Set<Integer> friendList;
//    private String aviator;
//    private String personalSignature;
    private String userIdFrom;
    private String userIdTo;
    private String createdTime;

    //评论系统


}
