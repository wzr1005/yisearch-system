package com.wzr.yi.service.Impl;

import com.wzr.yi.service.KankanService;
import com.wzr.yi.common.utils.ElasticSearchUtil;
import com.wzr.yi.common.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 11:48 上午
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class KankanServiceImpl implements KankanService {

    private final RedisUtils redisUtils;
    private final ElasticSearchUtil elasticSearchUtil;
    private final RedisTemplate redisTemplate;
    // 用户看单表    prefix+userId1	[item1,item2,item3]
    private String userKandanPrefix = "userKandan:";
    // 实体看单表    prefix+itemId1	[userid1, userid2]
    private String itemsKandanPrefix = "itemsKandan:";
    //用户点赞表
    private String userLikePrefix = "userLike:";

    //实体点赞表
    private String itemsLikePrefix = "itemsLike:";

    @Override
    public boolean addkankanItem(String userId, String itemId) {
        // 使⽤“：”冒号来体现分组
        // 表1，用户看单表，表2，实体看单表
        // 看单数据结构，userId:{"kandanjson": "addTime"}

        // 如果存在用户看单中，则先删除，再加入，
        redisUtils.delListByIndex(userKandanPrefix, itemId);
        // 加入用户看单，
        redisTemplate.opsForList().leftPush(userKandanPrefix, itemId);
        // 同时更新看单表
        redisTemplate.opsForSet().add(itemsKandanPrefix + userId, userId);
        return true;
    }

    @Override
    public void delkankanItem(String userId, String itemId) {
        redisUtils.delListByIndex(userKandanPrefix + userId, itemId);
    }

    @Override
    public Map<String, Object> loadkankanItem(String itemId) {
        return elasticSearchUtil.queryById("full", itemId);
    }

    @Override
    public Map<String, Object> getkankanItemInfo(String eid) {
        // 根据eid 去数据库中查询全量信息
        Map<String, Object> obj = elasticSearchUtil.queryById("full",eid);
        return obj;
    }

    @Override
    public List<Map<String, Object>> loadkandanUserList(String userId) {
        // 加载
        List<Map<String, Object>> list = redisTemplate.opsForList().range(userKandanPrefix + userId, 0, -1);
        return list;
    }


    @Override
    public boolean updateLikeForItem(String userId, String itemId) {
        // redis用list结构
        // 先查在不在redis中，
        if(redisTemplate.opsForSet().isMember(userLikePrefix + userId, itemId)){
            //更新用户点赞
            redisTemplate.opsForSet().remove(userLikePrefix + userId, itemId);
            //更新实体点赞
            redisTemplate.opsForSet().remove(itemsLikePrefix + itemId, userId);
            return false;
        }else{
            redisTemplate.opsForSet().add(userLikePrefix + userId + userId, itemId);
            redisTemplate.opsForSet().add(itemsLikePrefix + userId, itemId);
        }
        return true;
    }

}
