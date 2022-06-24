package com.wzr.kandan.service;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 11:42 上午
 */
public interface KankanService {
    // 看看管理
    public boolean addkankanItem(String itemId);
    public boolean delkankanItem(String itemId);
    public boolean loadkankanItem(String itemId);
    public boolean getkankanItemInfo(String itemId);
    // 点赞管理
    public boolean addLikeForItem(String itemId);
    public boolean delLikeForItem(String itemId);

    // click管理
    public boolean addClickForItem(String itemId);

    public boolean kankanToMysql();
}
