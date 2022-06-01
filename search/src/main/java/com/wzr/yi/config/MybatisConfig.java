//package com.wzr.yi.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
///**
// * @autor zhenrenwu
// * @date 2022/6/1 11:22 下午
// */
//@Configuration
//@MapperScan(basePackages = "mapper.mapper.dasheng", sqlSessionFactoryRef = "yiSearchSqlSessionFactory")
//public class MybatisConfig {
//    @Bean(name = "yiSearchSqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("yiSearchDataSource") DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dataSource);
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        bean.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
//        bean.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));
//        return bean.getObject();
//    }
//
//    @Bean(name = "yiSearchDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//}