package com.wzr.yi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @autor zhenrenwu
 * @date 2022/2/22 10:31 下午
 */
@Component
public class RedisUtils {

    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);
    private RedisTemplate<Object, Object> redisTemplate;

    public RedisUtils(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
