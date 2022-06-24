package com.wzr.friends.service;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 2:28 上午
 */
public interface FriendsService {
    public String addFriends(String selfId, String friendId);

    public String delFriends(String selfId, String friendId);

    public String sendMsg(String selfId, String friendId);

    public String delMsg(String selfId, String friendId);

    public String loadFriendsList(String selfId);

}
