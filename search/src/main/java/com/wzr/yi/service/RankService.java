package com.wzr.yi.service;

import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 12:25 下午
 */
public interface RankService {

    // 加载榜单
    public List<Map<String, Object>> loadRankList();

    // 最近热议
    public List<Map<String, Object>> loadHotDiscuss();


}
