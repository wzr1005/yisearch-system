package com.wzr.yi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzr.yi.entity.IndexProperty;
import com.wzr.yi.entity.IndexPropertyDto;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @autor zhenrenwu
 * @date 2022/6/2 2:35 上午
 */
@Data
@Slf4j
@Component
public class LoadTextByLineMulti {

    @Value("${file.testFile}")
    private String path;

    public LoadTextByLineMulti(String path) {
        this.path = path;
    }

    public LoadTextByLineMulti() {
    }

    /**
     * 泛型方法例子, 单线程读写
     * @param T
     * @param <T>
     * @return
     */
    @SneakyThrows
    public <T> List<T> loadLineDataSingle(Class T){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = null;
        List<T> list = new ArrayList<>();
        while (true) {
            try {
                if (!((line = bufferedReader.readLine()) != null)) break;
                JSONObject obj = (JSONObject) JSON.parse(line);
                T type1 = (T) new IndexProperty(obj);
                list.add(type1);
                Thread.sleep(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 多线程读写
     * @param T
     * @return
     */
    @SneakyThrows
    public List<IndexPropertyDto> loadLineDataMulti(Class T){ // 将类作为参数传入
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        };

        ExecutorService executorService = new ThreadPoolExecutor(1000, 10000, 1000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10000),
                threadFactory,
                (r, executor) -> {
                    if(!executor.isShutdown()){
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        String line = null;
        List<IndexPropertyDto> list = new ArrayList<>();
        while (true){
            if (!((line = bufferedReader.readLine()) != null)) break;
            executorService.submit(new loadRunnableThread(line, list));
        }
        executorService.shutdown();
        return list;
    }

    public class loadRunnableThread implements Runnable{

        private String line;
        private List<IndexPropertyDto> list;

        @SneakyThrows
        @Override
        public void run() {
            //
            JSONObject obj = (JSONObject) JSON.parse(line);
            IndexProperty indexProperty = (IndexProperty) new IndexProperty(obj);
            IndexPropertyDto indexPropertyDto = new IndexPropertyDto(indexProperty);
            list.add(indexPropertyDto);
            Thread.sleep(1);
        }
//78612 114694ms 多线程8000ms
        public loadRunnableThread(String line, List<IndexPropertyDto> list) {
            this.line = line;
            this.list = list;
        }
    }
    public static void main(String[] args) {
        long beginTime = System.currentTimeMillis();
        LoadTextByLineMulti loadTextByLineMulti = new LoadTextByLineMulti("/Users/zhenrenwu/tx/long_video_base_data/entity_diff/tupu_schema/tupu_all_entity_2022-05-12.txt");
//        List<IndexProperty> l = loadTextByLineMulti.loadLineDataSingle(IndexProperty.class);
        List<IndexPropertyDto> l = loadTextByLineMulti.loadLineDataMulti(IndexPropertyDto.class);
        System.out.println(l.size());
        long detTime = System.currentTimeMillis()-beginTime;
        log.info(String.format("finished, cost %d", detTime));
    }
}
