package com.wzr.yi.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @autor zhenrenwu
 * @date 2022/5/30 10:55 下午
 */
@RequiredArgsConstructor
@Slf4j
public class CglibProxy implements MethodInterceptor {
    private Object targetObject;

    public CglibProxy(Object targetObject) {
        this.targetObject = targetObject;

    }

//    public <T> Object createProxyedObj(T t){
//        //对外表现上看CreatProxyedObj，它只需要一个类型clazz就可以产生一个代理对象
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(t.getClass());
//        enhancer.setCallback();
//        return enhancer.create();
//    }
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        //预处理逻辑
        long beginTime = System.currentTimeMillis();
        log.info("executing " + method.getName() + ".....");
        Object result = method.invoke(targetObject,objects);
        //事后处理逻辑
        long detTime = System.currentTimeMillis();
        log.info(method.getName() + "finished, cost %ss",detTime);
        return result;
    }

}
