package com.wzr.yi.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @autor zhenrenwu
 * @date 2022/6/19 1:03 上午
 */
public class CustomproducerParameters {

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

        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        for (int i = 0; i < 50; i++) {
            kafkaProducer.send(new ProducerRecord<>("first", "zzzzhenren " + i));
        }
        kafkaProducer.close();
    }
}
