package com.wzr.friends.entity;

import lombok.Data;

/**
 * 好友信息
 * @autor zhenrenwu
 * @date 2022/6/24 1:45 上午
 */
@Data
public class Message {

    private int msgId;
    private String msgType;
    private String isSend;
    private String content;
    private String talker;
    private String createTime;

}
