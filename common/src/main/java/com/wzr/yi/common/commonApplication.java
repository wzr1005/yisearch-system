package com.wzr.yi.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @autor zhenrenwu
 * @date 2022/6/26 12:34 上午
 */
@SpringBootApplication
@EnableDiscoveryClient
public class commonApplication {
    public static void main(String[] args) {
        SpringApplication.run(commonApplication.class);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
