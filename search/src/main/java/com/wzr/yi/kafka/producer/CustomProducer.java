package com.wzr.yi.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @autor zhenrenwu
 * @date 2022/6/18 11:31 下午
 */
public class CustomProducer {

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
        // 2. 发送数据
        for (int i = 0; i < 5; i++) {
            kafkaProducer.send(new ProducerRecord<>("first", ",zhenren\t" + i));
            //阻塞方式 跟es差不多，加.get()就行
        }
        // 3. 关闭资源
        kafkaProducer.close();
    }
}
