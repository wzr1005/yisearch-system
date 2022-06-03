package com.wzr.yi.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * @autor zhenrenwu
 * @date 2022/6/2 10:14 下午
 */
@Component
@Slf4j
public class MyDataSource {
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
    @SneakyThrows
    @Bean(name = "druidDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource(){
        //加载配置文件
        log.info("初始化bean druidDataSource.....");


        log.info("初始化bean druidDataSource. finished....");
        return new DruidDataSource();
    }
}
