package com.wzr.yi.entity;

import lombok.Data;

import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/2/19 7:59 下午
 */
@Data
public class userKandan {

    // 放于redis中
    private Integer Id;
    private String kankanId;
    private List<String> kankanList;




}
