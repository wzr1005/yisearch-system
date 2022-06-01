package com.wzr.yi.service.impl;

import com.wzr.yi.Mapper.IndexPropertyMapper;
import com.wzr.yi.bean.EsRequetBody;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.exception.BadRequestException;
import com.wzr.yi.service.IndexService;
import com.wzr.yi.util.ElasticSearchUtil;
import com.wzr.yi.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
        ExecutorService executorService = new ThreadPoolExecutor(10000, 10000,
                1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10000),
                threadFactory,
                rejectedExecutionHandler
                );

//        String rawSql = "INSERT INTO index_property (name, alias, eid, serial_name, starring, " +
//                "director, gene_type, resource_wap, resource_pc, , resource_rank, " +
//                "year, hot_count, status, describe_info) VALUES ";

        List<IndexPropertyDto> insertList = new ArrayList<>();
        AtomicInteger index = new AtomicInteger();
        List<IndexPropertyDto> list = new ArrayList<>();
        list.add(new IndexPropertyDto(objList.get(index.getAndIncrement())));
        indexPropertyMapper.insertBatchRecord(list);
//        while (index.get() < objList.size()){
//            executorService.submit(()->{
//                List<IndexPropertyDto> list = new ArrayList<>();
//                while (index.get() < objList.size()){
//                    list.add(new IndexPropertyDto(objList.get(index.getAndIncrement())));
////                    index++;
//                    if(index.get() <objList.size() && insertList.size() == 2000 || index.get() == objList.size() - 1){
//                        indexPropertyMapper.insertBatchRecord(list);
//                    }
//                }
//            });
//        }
        long detTime = System.currentTimeMillis()-beginTime;
        log.info(String.format("finished, cost %d", detTime));

        return false;
    }

    @Override
    public String Testmybatis() {
        String version = indexPropertyMapper.findVersions();
        System.out.println(version);
        return version;
    }

    public static String listToStringJoin(List<?> l){
        return StringUtils.join(l.toArray(), ";");
    }
    public static int i = 0;
    public static ExecutorService executorService1;
    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        ThreadFactory r = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @SneakyThrows
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                if(!executor.isShutdown()){
                    // put会一直尝试，直到capacity空闲。
                    executor.getQueue().put(r);
                }
            }
        };
        executorService1 = new ThreadPoolExecutor(10, 100, 1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(100),
                r,
                handler
                );
        // 多线程每获取5个元素 打印
        while (i < 10000){
            executorService1.submit(()->{
                List<Integer> list = new ArrayList<>();
                while (i < 10000){
                    list.add(i);
                    i++;
                    if(i<10000&&list.size()==15 || i == 9999){
                        if(list.size()!=15){
                            System.out.println(list.toString() + "\t" + Thread.currentThread().getName() + '\t' + list.size());
                        }
                        list = null;
                    }
                }
            });
        }
        long detTime = System.currentTimeMillis()-beginTime;
        log.info(String.format("finished, cost %d", detTime));

    }
}
