//package com.wzr.yi.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
///**
// * @autor zhenrenwu
// * @date 2022/2/22 10:31 下午
// */
//@Component
//public class RedisUtils {
//
//    private static final Logger log = LoggerFactory.getLogger(RedisUtils.class);
//    private RedisTemplate<Object, Object> redisSearch;
//
//    public RedisUtils(RedisTemplate<Object, Object> redisSearch) {
//        this.redisSearch = redisSearch;
//    }
//
//
//    public boolean hasKey(String key) {
//        try {
//            return redisSearch.hasKey(key);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            return false;
//        }
//    }
//}
