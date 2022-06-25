package com.wzr.yi.common.utils;

/**
 * @autor zhenrenwu
 * @date 2022/6/25 12:01 上午
 */
public class SystemUtils {
    public static String getMethodName(){
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
