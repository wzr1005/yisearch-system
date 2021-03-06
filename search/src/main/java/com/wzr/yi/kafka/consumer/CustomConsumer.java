package com.wzr.yi.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @autor zhenrenwu
 * @date 2022/6/20 12:59 上午
 */
@Slf4j
@RestController
@RequestMapping("/kafka")
public class CustomConsumer {

    @GetMapping("/consume")
    public static void main(String[] args) {
        // 0 配置
        log.info("xixi");
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "9.135.76.106:9092");
        //反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put("auto.offset.reset", "earliest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        List<String> list = new ArrayList<>();
        list.add("first");
        kafkaConsumer.subscribe(list);
//        List<TopicPartition> topicPartitions = new ArrayList<>();
//        topicPartitions.add(new TopicPartition("first", 0));
//        kafkaConsumer.assign(topicPartitions);

        while (true){
//            System.out.println(1);
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            for(ConsumerRecord<String, String> consumerRecord : consumerRecords){
                System.out.println(consumerRecord);
            }

        }
    }
}
