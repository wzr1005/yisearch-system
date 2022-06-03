package com.wzr.yi.service;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/6/2 10:07 下午
 */
public class Test {
    public static void main(String[] args) {
        String value = "事实上'sss";
        if(value.contains("'")) {
            value = value.replace("'", "''");
        }
        System.out.println(value);
    }
//    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            list.add(i);
//        }
//        System.out.println(list.subList(0,3));
//    }
}
