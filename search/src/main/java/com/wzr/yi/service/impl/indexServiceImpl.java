package com.wzr.yi.service.impl;

import com.wzr.yi.bean.EsRequetBody;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.exception.BadRequestException;
import com.wzr.yi.service.IndexService;
import com.wzr.yi.util.ElasticSearchUtil;
import com.wzr.yi.util.MysqlUtils;
import com.wzr.yi.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Override
    public boolean BulkInsertMysql(List<IndexProperty> objList) {
        MysqlUtils mysqlUtils = new MysqlUtils();
        //一次插入2000条，多线程写入，引入消息队列,限制数据库10000个连接
        //同事又可以保证及时流量突然增大也不会占用服务器过多的资源。

        ExecutorService executorService = Executors.newFixedThreadPool(10000);
        String rawSql = "INSERT INTO index_property (name, alias, eid, serial_name, starring, " +
                "director, gene_type, resource_wap, resource_pc, , resource_rank, " +
                "year, hot_count, status, describe_info) VALUES ";
        class ThreadDb implements Runnable{

            private String exeSql;
            @Override
            public void run() {
                mysqlUtils.insertData(this.exeSql);
            }

            public ThreadDb(String exeSql) {
                this.exeSql = exeSql;
            }
        }
        String exeSql = rawSql;
        for (int i = 1; i <= objList.size(); i++) {
            //合并sql
            IndexProperty obj = objList.get(i);
            String part_sql = String.format("(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
                    obj.getName(), obj.getAlias(), obj.getEid(), obj.getSerialName(),obj.getStarring(),
                    obj.getDirector(), obj.getGeneType(), obj.getResourceWap(), obj.getResourcePc(),
                    obj.getRank(), obj.getYear(), obj.getHotCount(), obj.getDescribe());
            if(i == objList.size()) part_sql += ';';
            else part_sql += ',';
            rawSql += part_sql;
            if(i%1000==0||i == objList.size()){
                exeSql = rawSql + part_sql;
                executorService.submit(new ThreadDb(exeSql));
            }
        }

        return false;
    }
}
