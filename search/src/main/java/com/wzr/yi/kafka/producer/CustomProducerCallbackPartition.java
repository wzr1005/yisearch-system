package com.wzr.yi.kafka.producer;

import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @autor zhenrenwu
 * @date 2022/6/19 12:20 上午
 */
public class CustomProducerCallbackPartition {
    @SneakyThrows
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
        for (int i = 0; i < 50; i++) {
            // 指定分区
            // 不指定分区，按照key的hashcode对分区size取模
            // 例如加某种类型的订单表发到某一个分区。
            // 没有指定分区，也没有指定key，这个时候就是随机了，。
            kafkaProducer.send(new ProducerRecord<>("first","、,zhenren callback\t" + i), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    if(e == null){
                        System.out.println("主题： " + recordMetadata.topic() + "分区：" + recordMetadata.partition());
                    }
                }
            });
            // 为什么要睡眠，推测是按照时间戳来取的随机值
            Thread.sleep(20);
        }
        // 3. 关闭资源
        kafkaProducer.close();
    }
}
