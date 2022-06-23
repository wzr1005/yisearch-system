package com.wzr.yi.config.bean;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.wzr.yi.config.bean.LoginDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/23 1:12 上午
 */
@Component
@Slf4j
public class DruidLoginPool {
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

    public DruidDataSource druidDataSource;

    public List<Map<String, Object>> executeSqlQuery(String sql) {
        // 需要执行时，从连接池拿一个就行了
        Statement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> list = null;
        try {
            connection = druidDataSource.getConnection();
        } catch (SQLException e) {
            log.error("获取连接失败\t" + sql);
        }
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            list = parseResultSet(resultSet);
        } catch (SQLException e) {
            log.error("获取连接失败\t" + sql);
        }

        return list;
    }

    public boolean executeSqlUpdate(String sql) {
        // 需要执行时，从连接池拿一个就行了
        Statement statement = null;
        Connection connection = null;
        try {
            connection = druidDataSource.getConnection();
        } catch (SQLException e) {
            log.error("获取连接失败\t" + sql);
        }
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("执行sql失败\t" + sql);
            return false;
        }
        return true;
    }
    @SneakyThrows
    public List<Map<String, Object>> parseResultSet(ResultSet resultSet){
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<Map<String, Object>> resultList = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, Object> dataMap = new HashMap<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                dataMap.put(metaData.getColumnLabel(i), resultSet.getObject(i));
            }                resultList.add(dataMap);
        }

        return resultList;
    }

    public DruidLoginPool(DruidDataSource loginDruidDataSource){
        log.info("Spring Druid Pool Login 初始化");
        if(loginDruidDataSource == null){
            log.error("datasource not set");
        }
        this.druidDataSource = loginDruidDataSource;
    }

    //    public DruidPool() {
//        System.out.println("Spring默认无参构造");
//    }
    /*druid监控，*/
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //登录druid后台监控的账号密码
        servletRegistrationBean.addInitParameter("loginUsername", "root");
        servletRegistrationBean.addInitParameter("loginPassword", "root");
        //是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        return servletRegistrationBean;
    }
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        //拦截的路径
        filterRegistrationBean.addUrlPatterns("/*");
        //不需要拦截的信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean
    public Filter statFilter(){
        StatFilter statFilter=new StatFilter();
        statFilter.setSlowSqlMillis(1000);// 执⾏超过此时间的为慢sql，毫秒
        statFilter.setLogSlowSql(true);// 是否打印慢⽇志
        statFilter.setMergeSql(true);// 是否将⽇志合并起来
        return statFilter;
    }
}
