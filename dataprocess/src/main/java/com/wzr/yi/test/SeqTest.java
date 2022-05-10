package com.wzr.yi.test;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * @autor zhenrenwu
 * @date 2022/5/10 8:57 下午
 */
@Data
public class SeqTest implements Serializable {
    private final static long twepoch = 1288834974657L;
    private final int age = 29;
    private String name = "zhenrenwu";

    public SeqTest() {
    }

    public SeqTest(int age, String name) {
        this.name = name;
    }


    public static void main(String[] args) {
        SeqTest seqTest = new SeqTest();
        System.out.println(seqTest.toString());
        System.out.println(JSON.toJSONString(seqTest));
    }
}
