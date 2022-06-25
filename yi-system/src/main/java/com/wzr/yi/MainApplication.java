package com.wzr.yi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @autor zhenrenwu
 * @date 2022/2/21 10:05 下午
 */
// scanBasePackages 加载bean扫描的包，之前一直扫描不到，不过为什么他会默认扫描com.wzr.yi; 可能自动包含他自己的包
@SpringBootApplication
@EnableDiscoveryClient
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
