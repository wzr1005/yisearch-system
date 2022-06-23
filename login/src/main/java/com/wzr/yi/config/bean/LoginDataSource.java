package com.wzr.yi.config.bean;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @autor zhenrenwu
 * @date 2022/6/23 10:42 上午
 */
@Component
@Slf4j
public class LoginDataSource {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.initialSize}")
    private String initialSize;

    @Value("${spring.datasource.maxActive}")
    private String maxActive;

    @Value("${spring.datasource.maxWait}")
    private String maxWait;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;


    //配置DataSource数据源
    @Bean(name = "loginDruidDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource loginDruidDataSource(){
        //加载配置文件
        log.info("初始化bean login druidDataSource. finished....");
        return new DruidDataSource();
    }
}
