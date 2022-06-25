package com.wzr.yi.service;

import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 11:42 上午
 */
public interface KankanService {
    // 看看管理
    public boolean addkankanItem(String userid, String itemId);
    public void delkankanItem(String userId, String itemId);

    public Map<String, Object> loadkankanItem(String itemId);

    /**
     * 返回用户的看单列表
     * @param userId
     * @return
     */
    public List<Map<String, Object>> loadkandanUserList(String userId);
    public Map<String, Object> getkankanItemInfo(String itemId);
    // 点赞管理
    public boolean updateLikeForItem(String userId, String itemId);

    // 获取点赞状态

}
