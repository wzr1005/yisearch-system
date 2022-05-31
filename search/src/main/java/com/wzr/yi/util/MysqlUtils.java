package com.wzr.yi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @autor zhenrenwu
 * @date 2022/5/30 11:32 下午
 */
@Slf4j
@Component
public class MysqlUtils {
    private JdbcTemplate jdbcTemplate;

    public int insertData(String sql){
        if(jdbcTemplate == null){
            jdbcTemplate = new JdbcTemplate();
        }
        return jdbcTemplate.update(sql);
    }
}
