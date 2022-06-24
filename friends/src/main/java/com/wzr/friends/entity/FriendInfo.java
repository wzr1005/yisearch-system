package com.wzr.friends.entity;

import lombok.Data;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 2:13 上午
 */
@Data
public class FriendInfo {
    private String userId;
    public String userCount;
    private String nickName;
    public String gender;
    private String avatarPath;
    public String role;
    public String growthScore;
    public String growthLevel;

}
