package com.wzr.yi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

/**
 * @autor zhenrenwu
 * @date 2022/5/30 11:32 下午
 */
@Slf4j
@Component
public class MysqlUtils {

    private String username;
    private String password;
    private JdbcTemplate jdbcTemplate;

    public int insertData(String sql){
        if(jdbcTemplate == null){
            jdbcTemplate = new JdbcTemplate();
        }
        return jdbcTemplate.update(sql);
    }
    //配置DataSource数据源
//    @Bean
//    public DataSource dataSource(){
//        DruidDataSource dataSource = new DruidDataSource();
//        //啥子数据源的名称和密码等等
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        //设置连接的url
//        dataSource.setUrl(url);
//        //设置连接的驱动
//        dataSource.setDriverClassName(driverClassName);
//        log.info("初始化bean dataSource.....");
//        return dataSource;
//    }

}
