package com.wzr.yi.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @autor zhenrenwu
 * @date 2022/5/30 10:41 下午
 */
@Slf4j
public class DynamicProxyInvocation implements InvocationHandler {
    private Object target;

    public DynamicProxyInvocation(Object target) {
        this.target = target;
    }

    /**
     * 这个代理的主要作用就是，对于需要使用他的方法，增加了预处理逻辑，和事后处理逻辑
     * 从而实现了，AOP 面向切面编程。
     * 有很广泛的应用，比如纪录接口调用统计，纪录日志等等。
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        //预处理逻辑
        long beginTime = System.currentTimeMillis();
        log.info("executing " + method.getName() + ".....");

        /* 真实的方法调用 */
        result = method.invoke(target, args);
        //事后处理逻辑
        long detTime = System.currentTimeMillis();
        log.info(method.getName() + "finished, cost %ss",detTime);
        return result;
    }
}
