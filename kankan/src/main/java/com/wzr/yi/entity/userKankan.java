package com.wzr.yi.entity;

import lombok.Data;

import java.util.List;

/**
 * 用户看单管理，放于redis
 * @autor zhenrenwu
 * @date 2022/6/24 11:45 上午
 */
@Data
public class userKankan {
    // 放于redis中
    private Integer Id;
    private String kankanId;
    private List<String> kankanList;
}
