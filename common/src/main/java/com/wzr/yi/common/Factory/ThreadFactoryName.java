package com.wzr.yi.common.Factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @autor zhenrenwu
 * @date 2022/6/4 1:57 下午
 */
@Component
@Slf4j
public class ThreadFactoryName implements ThreadFactory {

    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public ThreadFactoryName() {
        this("yiSerach-pool");
    }

    private ThreadFactoryName(String name){
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        //此时namePrefix就是 name + 第几个用这个工厂创建线程池的
        this.namePrefix = name +
                POOL_NUMBER.getAndIncrement();
    }

    @Override
    public Thread newThread(Runnable r) {
        //此时线程的名字 就是 namePrefix + -thread- + 这个线程池中第几个执行的线程
        Thread t = new Thread(group, r,
                namePrefix + "-thread-"+threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
//        log.info(String.format("执行线程%d", POOL_NUMBER.get()));
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
