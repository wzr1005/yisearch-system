package com.wzr.yi.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @autor zhenrenwu
 * @date 2022/6/19 12:20 上午
 */
public class CustomProducerCallback {
    public static void main(String[] args) {
        // 0. 配置
        Properties properties = new Properties();
        // 连接集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "9.135.76.106:9093");
        // 指定序列化类型
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 1. 创建kafka生产者对象 <key, hello>
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);

        // 自定义分区器,可以进行路由，或者过滤掉脏数据等作用
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyPartition.class.getName());
        // 2. 发送数据
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first", "hello,zhenren baid\t" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e == null){
                        System.out.println("主题： " + recordMetadata.topic() + "分区：" + recordMetadata.partition());
                    }
                }
            });
        }
        // 3. 关闭资源
        kafkaProducer.close();
    }
}
