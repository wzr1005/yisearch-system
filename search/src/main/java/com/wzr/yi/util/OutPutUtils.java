package com.wzr.yi.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor zhenrenwu
 * @date 2022/6/1 9:22 下午
 */
public class OutPutUtils {
    public static <T, A> A ObjectToString(T t){
        if(t == null) return (A)"";
        A a = (A) "";
        if(t instanceof String || t instanceof Integer || t instanceof Float){
            return (A) t.toString();
        }
        if(t instanceof List){
            if(((List<?>) t).get(0) instanceof String){
                return (A)t.toString();
            }
        }
        return a;

    }

    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add("haha");
        a.add("hehe");
        String res = StringUtils.join(a.toArray(), ";");
        System.out.println(res);
    }
}
