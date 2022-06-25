package com.wzr.yi.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 10:47 下午
 */
public class DateUtils {
    public static String nowTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String nowYear(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy"));
    }
    public static String nowDays(){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    public static void main(String[] args) {
        System.out.println(nowTime());
        System.out.println(nowYear());
    }
}
