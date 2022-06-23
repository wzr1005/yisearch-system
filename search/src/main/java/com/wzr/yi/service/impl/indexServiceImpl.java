package com.wzr.yi.service.impl;

import com.wzr.common.exception.BadRequestException;
import com.wzr.yi.Factory.ThreadFactoryName;
//import com.wzr.yi.Mapper.IndexPropertyMapper;
import com.wzr.yi.bean.EsRequestBody;
import com.wzr.yi.config.DruidPool;
import com.wzr.yi.config.MysqlConfig;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.service.IndexService;
import com.wzr.yi.util.ElasticSearchUtil;
import com.wzr.yi.util.MyStringUtils;
//import com.wzr.yi.util.RedisUtils;
import com.wzr.yi.util.ThreadPoolUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.ExecutorException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.wzr.yi.util.MyStringUtils.generateSqlBatch;

/**
 * @autor zhenrenwu
 * @date 2022/2/22 9:57 下午
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class indexServiceImpl implements IndexService {

    private final ElasticSearchUtil elasticSearchUtil;
//    private final RedisUtils redisUtils;
//    private final IndexPropertyMapper indexPropertyMapper;
    private final MyStringUtils myStringUtils;
    //注意这种默认就是单例，但是可以用他来创建新的其他实例
    private final MysqlConfig mysqlConfig;
    private final DruidPool druidPool;
    @Override
    public ResponseEntity createIndex() {
        return null;
    }

    @Override
    public ResponseEntity createIndex(EsRequestBody esRequestBody) throws ExecutionException, InterruptedException {
        String indexName = esRequestBody.getIndexName();
        if(indexName == null){
            throw new BadRequestException("索引名字不能为空");
        }
        CreateIndexRequest index = new CreateIndexRequest(indexName);
        //定义json格式映射
        if(esRequestBody.getJson()!=null){
            index.mapping(indexName, esRequestBody.getJson(), XContentType.JSON);
        }
        return new ResponseEntity(elasticSearchUtil.createIndex(esRequestBody), HttpStatus.OK);
    }

//    @Override
//    public boolean redis() {
//        return redisUtils.hasKey("1");
//    }

    @Override
    public boolean BulkInsertMysql(List<IndexPropertyDto> objList) {
        long beginTime = System.currentTimeMillis();
        ThreadFactoryName threadFactory = new ThreadFactoryName();
        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
            @SneakyThrows
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if(!executor.isShutdown()){
                    executor.getQueue().put(r);
                }
            }
        };
        ExecutorService executorService = new ThreadPoolExecutor(20, 50,
                1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1000),
                threadFactory,
                rejectedExecutionHandler
        );
        AtomicInteger index = new AtomicInteger();
        log.info("开始多线程写入到数据库。。。");
        multiInsert(objList);
        return true;
    }

    public void multiInsert(List<IndexPropertyDto> objList){
        long beginTime = System.currentTimeMillis();
        //一次插入2000条，多线程写入，引入消息队列,限制数据库10000个连接
        //同事又可以保证及时流量突然增大也不会占用服务器过多的资源。

        ThreadFactoryName threadFactory = new ThreadFactoryName();
        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
            @SneakyThrows
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if(!executor.isShutdown()){
                    executor.getQueue().put(r);
                }
            }
        };
        int core = 20;
        int maxThread = 1000;
        int queueNum = 400;
        int step = 2000;
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(core, maxThread,
                1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueNum),
                threadFactory,
                rejectedExecutionHandler
        );

        log.info("开始多线程写入到数据库。。。");
        int listIndex = 0;
        AtomicInteger insertNum = new AtomicInteger(0);
//        int insertNum = 0;
        while(listIndex < objList.size()){
            int finalListIndex = listIndex;
            try{
//                log.info(String.format("任务提交", finalListIndex));
                Future future = threadPoolExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        //内部类不允许访问局部变量
                        String sqlBatch = generateSqlBatch(objList.subList(finalListIndex,finalListIndex+step));
                        druidPool.executeSqlUpdate(sqlBatch);
                        insertNum.addAndGet(step);
                    }
                });
            }catch (ExecutorException executorException){
                log.error(String.format("提交任务失败 %d", finalListIndex));
            }
            listIndex += step;
        }
        //list剩余元素插入
        if(listIndex != objList.size()){
            log.info("list剩余元素插入");
            String sqlBatch = generateSqlBatch(objList.subList(listIndex-step,objList.size()));
            druidPool.executeSqlUpdate(sqlBatch);
            insertNum.addAndGet(objList.size() - listIndex + step);
        }

        if(ThreadPoolUtils.isCompleted(threadPoolExecutor)){
            long detTime = System.currentTimeMillis()-beginTime;
            log.info(String.format("%s finished, cost %d",Thread.currentThread().getStackTrace()[Thread.currentThread().getStackTrace().length-1], detTime));
        }
        if(threadPoolExecutor.isShutdown()){
            System.out.println(insertNum.get());
        }
    }


    @Override
    public String Testmybatis() {
//        String version = indexPropertyMapper.findVersions();
//        System.out.println(version);
//        return version;
        return null;
    }

    @Override
    public List<Map<String, Object>> testDruid() {
        List<Map<String, Object>> list = null;
        list = druidPool.executeSqlQuery("select * from index_property limit 1");
        return list;
    }

    @Override
    public List<Map<String, Object>> sortResult(List<Map<String, Object>> result) {
        // 召回排序，逻辑
        // 1. 如果name全对，加10分
        // 2。 如果名字不对，但是属于series一部分，加八分
        // 3。 如果query中包含数据
        return null;
    }

}
