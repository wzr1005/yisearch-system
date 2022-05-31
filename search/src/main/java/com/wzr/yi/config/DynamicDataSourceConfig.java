//package com.wzr.yi.config;
//
//import lombok.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
//
///**
// * @autor zhenrenwu
// * @date 2022/5/4 10:20 下午
// */
//@Configuration
//public class DynamicDataSourceConfig {
//    //如果是IOC容器中，同一个类型有多个bean，则bean的名称为方法的名称
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid.first")
//    public DataSource firstDataSource(){
//        return DruidDataSourceBuilder.create().build();
//    }
//    @Bean
//    public DataSource dataSource(){
//        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
//        @Value("")
//        driverManagerDataSource.setUrl();
//    }
//}
