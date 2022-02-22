package com.wzr.yi.service.impl;

import com.wzr.yi.service.indexService;
import com.wzr.yi.util.ElasticSearchUtil;
import com.wzr.yi.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @autor zhenrenwu
 * @date 2022/2/22 9:57 下午
 */
@RequiredArgsConstructor
@Service
public class indexServiceImpl implements indexService {

    private final ElasticSearchUtil elasticSearchUtil;
    private final RedisUtils redisUtils;
    @Override
    public boolean createIndex() {

        return elasticSearchUtil.isIndexExist("dangdang");
    }

    @Override
    public boolean redis() {
        return redisUtils.hasKey("1");
    }
}
