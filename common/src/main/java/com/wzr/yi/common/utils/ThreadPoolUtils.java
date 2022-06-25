package com.wzr.yi.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @autor zhenrenwu
 * @date 2022/6/24 6:12 下午
 */
public class ThreadPoolUtils {
    public static boolean isCompleted(ExecutorService executorService){
        executorService.shutdown();
        long beginTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - beginTime < 10000){
            if(executorService.isTerminated()) return true;
        }
        return false;
    }
    public static ThreadPoolExecutor getThreadPoolNoReject(){
        //计算型的核心线程数应为核心数+1， io型的应为2*核心数
        //如果是CPU密集型应用，则线程池大小设置为N+1
        //如果是IO密集型应用，则线程池大小设置为2N+1（因为io读数据或者缓存的时候，线程等待，此时如果多开线程，能有效提高cpu利用率）
        //如果一台服务器上只部署这一个应用并且只有这一个线程池，那么这种估算或许合理，具体还需自行测试验证。
        //但是，IO优化中，这样的估算公式可能更适合：
        //最佳线程数目 = （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目
        /*相应比乘以cpu数目*/
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10000),
                r->{return new Thread(r);},
                (r,executor)->{
                    if(!executor.isShutdown()){
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return threadPoolExecutor;
    }
    public static ThreadPoolExecutor getThreadPoolNoReject(int core){

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(core, core, 1000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10000),
                r->{return new Thread(r);},
                (r,executor)->{
                    if(!executor.isShutdown()){
                        try {
                            executor.getQueue().put(r);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return threadPoolExecutor;
    }
}
