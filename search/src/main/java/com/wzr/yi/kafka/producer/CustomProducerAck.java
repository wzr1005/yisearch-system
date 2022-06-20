package com.wzr.yi.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @autor zhenrenwu
 * @date 2022/6/19 1:29 上午
 */
public class CustomProducerAck {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "9.135.76.106:9092");
        // 指定序列化类型
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 1. 创建kafka生产者对象 <key, hello>

        // 自定义分区器,可以进行路由，或者过滤掉脏数据等作用
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartition.class.getName());
        // 缓冲区大小
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        //批次大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        // linger.ms
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 2);

        // 压缩 gzip snappy lz4 snappy用的比较多
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        // 应答方式
        properties.put(ProducerConfig.ACKS_CONFIG, -1);

        // 重试次数，默认为int的最大值
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");
        // 精确一次，== 幂等性 + 至少一次 <PID, Partition, SeqNumber> 保证唯一。
        // Kafka集群每次启动分配一个PID，幂等性只能保证是在单分区单绘画内不重复。enable.idempotence 幂等性默认为true

        /*
        生产者事务 说明，开启事务，必须开启幂等性
        组件： 事务协调器， 存储事务信息的特殊主题， 通过transactional.id来找broker主机
        默认有50个分区，事务划分是根据transactional.id的hashcode值%50，计算出该事务是属于哪个分区。
        该分区Leader副本所在的broker节点即为这个transactional.id对应的transaction Coordinator节点

        如果客户端挂了，它重启之后也能继续处理未完成的事务

         */

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

    }
}
