package com.wzr.yi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @autor zhenrenwu
 * @date 2022/2/21 10:05 下午
 */
@SpringBootApplication
@MapperScan(value = "com.wzr.yi.Mapper")
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
