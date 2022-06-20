package com.wzr.yi.kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @autor zhenrenwu
 * @date 2022/6/19 12:43 上午
 */
public class MyPartition implements Partitioner {

    @Override
    public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        String msg = (String) o1;
        int partition;
        if(msg.contains("baidu")){
            partition = 0;
        }else{
            partition = 1;
        }
        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
