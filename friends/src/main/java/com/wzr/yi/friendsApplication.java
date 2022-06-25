package com.wzr.yi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @autor zhenrenwu
 * @date 2022/6/25 11:43 下午
 */
@SpringBootApplication
@EnableDiscoveryClient
public class friendsApplication {
    public static void main(String[] args) {
        SpringApplication.run(friendsApplication.class);
    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
