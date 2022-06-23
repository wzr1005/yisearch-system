package com.wzr.yi.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @autor zhenrenwu
 * @date 2022/6/1 10:07 上午
 * 自动配置
 */
@Slf4j
@Component()
//@Scope("prototype")//作用域  默认为singleton，即为单例
public class MysqlConfig {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate JdbcTemplate(){
        log.info("初始化bean dataSource.....");
        DruidDataSource dataSource = new DruidDataSource();
        //啥子数据源的名称和密码等等
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        //设置连接的url
        log.info("初始化bean dataSource. url....");
        dataSource.setUrl(url);
        //设置连接的驱动
        dataSource.setDriverClassName(driverClassName);
        log.info("初始化bean dataSource. finished....");
        return new JdbcTemplate(dataSource);
    }

    //配置DataSource数据源
//    @Bean
//    public DataSource dataSource(){
//        log.info("初始化bean dataSource.....");
//        DruidDataSource dataSource = new DruidDataSource();
//        //啥子数据源的名称和密码等等
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        //设置连接的url
//        log.info("初始化bean dataSource. url....");
//        dataSource.setUrl(url);
//        //设置连接的驱动
//        dataSource.setDriverClassName(driverClassName);
//        log.info("初始化bean dataSource. finished....");
//        return dataSource;
//    }

//    public JdbcTemplate getJdbcTemplate(){
//        return new JdbcTemplate(dataSource());
//    }
}
