package com.wzr.yi.dto;

import com.wzr.yi.entity.IndexProperty;

/**
 * @autor zhenrenwu
 * @date 2022/6/26 2:29 上午
 */
public class test {
    public static void main(String[] args) {
        IndexProperty indexProperty = new IndexProperty();
        indexProperty.setDescribeInfo("aaa");
        System.out.println(indexProperty.getDescribeInfo());;
    }
}
