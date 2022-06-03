package com.wzr.yi.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.wzr.yi.Mapper.IndexPropertyMapper;
import com.wzr.yi.bean.EsRequetBody;
import com.wzr.yi.config.DruidPool;
import com.wzr.yi.config.MyDataSource;
import com.wzr.yi.config.MysqlConfig;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.exception.BadRequestException;
import com.wzr.yi.service.IndexService;
import com.wzr.yi.util.ElasticSearchUtil;
import com.wzr.yi.util.MyStringUtils;
import com.wzr.yi.util.MysqlUtils;
import com.wzr.yi.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static com.wzr.yi.util.MyStringUtils.generateSqlBatch;
import static com.wzr.yi.util.MyStringUtils.getValueOrDefault;

/**
 * @autor zhenrenwu
 * @date 2022/2/22 9:57 下午
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class indexServiceImpl implements IndexService {

    private final ElasticSearchUtil elasticSearchUtil;
    private final RedisUtils redisUtils;
    private final IndexPropertyMapper indexPropertyMapper;
    private final MyStringUtils myStringUtils;
    //注意这种默认就是单例，但是可以用他来创建新的其他实例
    private final MysqlConfig mysqlConfig;
    private final DruidPool druidPool;
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
    public boolean BulkInsertMysql(List<IndexPropertyDto> objList) {
        long beginTime = System.currentTimeMillis();
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
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
        CountDownLatch countDownLatch = new CountDownLatch(objList.size());
        int step = 100;
        int listIndex = 0;
//        while (index.get() < objList.size()){
//            // 多线程读取队列中数据，一次读满1000条之后，写入到mysql。
//            System.out.println();
//            indexPropertyMapper.insertBatchRecord(objList.subList(index.get(), index.getAndIncrement() + 1));
////            executorService.submit(()->{
////                //单线程内
////                while (index.get() < objList.size()) {  // index为拿到的令牌
////                    indexPropertyMapper.insertBatchRecord(objList.subList(index.get(), index.get() + 1));
////                }
////            });
////            break;
//        }
        long detTime = System.currentTimeMillis()-beginTime;
        log.info(String.format("finished, cost %d", detTime));
        executorService.shutdown();
        return true;
    }

    public void multiInsert(List<IndexPropertyDto> objList){
        long beginTime = System.currentTimeMillis();
        //一次插入2000条，多线程写入，引入消息队列,限制数据库10000个连接
        //同事又可以保证及时流量突然增大也不会占用服务器过多的资源。

        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
            @SneakyThrows
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if(!executor.isShutdown()){
                    executor.getQueue().put(r);
                }
            }
        };
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(200, 200,
                1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(100),
                threadFactory,
                rejectedExecutionHandler
        );

        AtomicInteger index = new AtomicInteger();
        log.info("开始多线程写入到数据库。。。");
        Long druidDataSourceBackup = druidPool.druidDataSource.getID();
        CountDownLatch countDownLatch = new CountDownLatch(objList.size());
        int step = 10;
        int listIndex = 0;
        while(listIndex < objList.size()){
            int finalListIndex = listIndex;
            threadPoolExecutor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    //内部类不允许访问局部变量
                    String sqlBatch = generateSqlBatch(objList.subList(finalListIndex,finalListIndex+step));
                    druidPool.executeSqlUpdate(sqlBatch);
                }
            });
            if(druidDataSourceBackup != druidPool.druidDataSource.getID()){
                log.error("新建了其他连接池");
            }
            System.out.println(threadPoolExecutor.getPoolSize() + "Threads running....");
            if(druidPool.druidDataSource.getConnectCount() > 200){
                System.out.println("ActiveCount: " +
                        druidPool.druidDataSource.getActiveCount() +
                        "\tConnectCount" + druidPool.druidDataSource.getConnectCount());

            }
            listIndex += step;
        }
        //list剩余元素插入
        if(listIndex >= objList.size()){
            String sqlBatch = generateSqlBatch(objList.subList(listIndex-step,objList.size()));
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.execute(sqlBatch);
        }
        long detTime = System.currentTimeMillis()-beginTime;
        log.info(String.format("finished, cost %d", detTime));
        threadPoolExecutor.shutdown();
    }

    @Override
    public String Testmybatis() {
        String version = indexPropertyMapper.findVersions();
        System.out.println(version);
        return version;
    }

    @Override
    public List<Map<String, Object>> testDruid() {
        List<Map<String, Object>> list = null;
        list = druidPool.executeSqlQuery("select * from index_property limit 1");
        return list;
    }


    public static int i = 0;
    public static ExecutorService executorService1;

//    public static void main(String[] args) {
//        long beginTime = System.currentTimeMillis();
//        ThreadFactory r = new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r);
//            }
//        };
//        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
//            @SneakyThrows
//            @Override
//            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                if(!executor.isShutdown()){
//                    // put会一直尝试，直到capacity空闲。
//                    executor.getQueue().put(r);
//                }
//            }
//        };
//        executorService1 = new ThreadPoolExecutor(10, 100, 1000L, TimeUnit.MILLISECONDS,
//                new ArrayBlockingQueue<>(100),
//                r,
//                handler
//                );
//        // 多线程每获取5个元素 打印
//        while (i < 10000){
//            executorService1.submit(()->{
//                List<Integer> list = new ArrayList<>();
//                while (i < 10000){
//                    list.add(i);
//                    i++;
//                    if(i<10000&&list.size()==15 || i == 9999){
//                        if(list.size()!=15){
//                            System.out.println(list.toString() + "\t" + Thread.currentThread().getName() + '\t' + list.size());
//                        }
//                        list = null;
//                    }
//                }
//            });
//        }
//        long detTime = System.currentTimeMillis()-beginTime;
//        log.info(String.format("finished, cost %d", detTime));
//
//    }
}
