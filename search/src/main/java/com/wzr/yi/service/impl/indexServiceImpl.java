package com.wzr.yi.service.impl;

import com.wzr.yi.bean.EsRequetBody;
import com.wzr.yi.exception.BadRequestException;
import com.wzr.yi.service.IndexService;
import com.wzr.yi.util.ElasticSearchUtil;
import com.wzr.yi.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @autor zhenrenwu
 * @date 2022/2/22 9:57 下午
 */
@RequiredArgsConstructor
@Service
public class indexServiceImpl implements IndexService {

    private final ElasticSearchUtil elasticSearchUtil;
    private final RedisUtils redisUtils;

    @Override
    public ResponseEntity createIndex() {
        return null;
    }

    @Override
    public ResponseEntity createIndex(EsRequetBody esRequetBody) throws ExecutionException, InterruptedException {
        String indexName = esRequetBody.getIndexName();
        if(indexName == null){
            throw new BadRequestException("索引名字不能为空");
        }
        CreateIndexRequest index = new CreateIndexRequest(indexName);
        //定义json格式映射
        if(esRequetBody.getJson()!=null){
            index.mapping(indexName,esRequetBody.getJson(), XContentType.JSON);
        }
        return new ResponseEntity(elasticSearchUtil.createIndex(esRequetBody), HttpStatus.OK);
    }

    @Override
    public boolean redis() {
        return redisUtils.hasKey("1");
    }
}
