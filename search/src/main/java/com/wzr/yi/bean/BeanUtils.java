package com.wzr.yi.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.persistence.criteria.Order;
import java.io.*;
import java.lang.reflect.Field;

/**
 * @autor zhenrenwu
 * @date 2022/6/10 12:06 上午
 */
@Slf4j
@Configuration
public class BeanUtils<T> {

    // 序列号方式实现深拷贝
//    public T deepClone(T object){
//        Field[] fields = object.getClass().getDeclaredFields();
//        for(Field field : fields){
//
//        }
//    }
}
