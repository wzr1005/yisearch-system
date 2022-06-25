package com.wzr.yi.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/9 11:40 下午
 * 常量类放置常量属性
 */
public class Constant {
    // 所有种类
    public static final String[] AllCategory = new String[]{
            "film", "teleplay", "tvshow", "cartoon"};
    public static final String[] resourceListCN = new String[]{
            "腾讯", "优酷", "哔哩哔哩"
    };
    public static final String[] ImpurityArr = new String[]{
            "在线观看", "怎么样"
    };
    public static final String[] bangdanList = new String[]{
            "院线电影票房榜", "院线电影热搜榜", "全网电影热搜榜", "全网电影飙升榜",
            "全网电视剧热搜榜", "全网电视剧飙升榜",
            "全网综艺热搜榜", "全网综艺飙升榜",
            "全网卡通热搜榜", "全网卡通飙升榜"
    };
    public static final Map<String, String> BANGDANMAP = new HashMap<>();
    static {
        BANGDANMAP.put("film", "全网电影热搜榜");
        BANGDANMAP.put("teleplay", "全网电视剧热搜榜");
        BANGDANMAP.put("tvshow", "全网综艺热搜榜");
        BANGDANMAP.put("cartoon", "全网卡通热搜榜");
    }
    public static final String KandanFullInfoPrefix = "kandanFullInfo:";
}
