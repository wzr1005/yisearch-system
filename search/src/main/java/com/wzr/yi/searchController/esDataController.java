package com.wzr.yi.searchController;

import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.entity.IndexPropertyDto;
import com.wzr.yi.util.LoadTextByLineMulti;
import com.wzr.yi.common.utils.ThreadPoolUtils;
import com.wzr.yi.common.utils.ElasticSearchUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

import static com.wzr.yi.common.utils.MyStringUtils.ObjectToString;
import static com.wzr.yi.common.utils.ThreadPoolUtils.isCompleted;


/**
 * @autor zhenrenwu
 * @date 2022/6/18 4:22 上午
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/esData")
@Slf4j
public class esDataController {

    private final ElasticSearchUtil elasticSearchUtil;

    @GetMapping("/esBulk")
    public void BulkInsertEs(String index, String type, String path){
//        String path = "/Users/zhenrenwu/Documents/txwd/teleplay_myself.txt";
        List<String> list = new LoadTextByLineMulti().loadLineStringList(path);
//        ArrayBlockingQueue<String> toInsertList = new ArrayBlockingQueue<String>(list.size());
        List<String> toInsertList = new ArrayList<>();
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtils.getThreadPoolNoReject();
        //将读取的list转换为List<Object> 28827条数据
        CountDownLatch countDownLatch = new CountDownLatch(list.size());
//        Vector<String> toInsertList = new Vector<>();
        long beginTime = System.currentTimeMillis();
        log.info("开始处理读取数据。。。");
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        list.forEach(entity ->{
            threadPoolExecutor.submit(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    try{
                        String valueJson = entity;
                        if(index.contains("index")){
                            IndexPropertyDto indexPropertyDto = new IndexPropertyDto(new IndexProperty(entity));
                            valueJson = ObjectToString(indexPropertyDto);
                        }
                        synchronized (toInsertList){
                            toInsertList.add(valueJson);
                        }
                        countDownLatch.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(entity);
                    }
                }
            });
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(isCompleted(threadPoolExecutor)){
            log.info(String.format("处理总耗时 %d", System.currentTimeMillis()-beginTime));
            System.out.println(toInsertList.size());
        }
//        List<String> toInsertList1 = new ArrayList<>(toInsertList);
        elasticSearchUtil.BulkInsertDocument(index, type, toInsertList);
    }
}
