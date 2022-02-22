package com.wzr.yi.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @autor zhenrenwu
 * @date 2022/2/20 3:12 下午
 */
@Slf4j
@Configuration
public class ElasticsearchConfig {

    @Value("${es.host}")
    private String host;

    @Value("${es.port}")
    private int port;

    /**
     * 集群名称
     */
    @Value("${es.cluster-name}")
    private String clusterName;

    /**
     * 连接池
     */
    @Value("${es.pool}")
    private String poolSize;

    @Bean(name = "transportClient")
    public TransportClient transportClient() throws UnknownHostException {
        // 配置信息
//            Settings esSetting = Settings.builder()
//                    .put("cluster.name", clusterName) //集群名字
////                    .put("client.transport.sniff", true)//增加嗅探机制，找到ES集群
//                    .put("thread_pool.search.size", Integer.parseInt(poolSize))//增加线程池个数，暂时设为5
//                    .build();
        //配置信息Settings自定义
        TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY);
        log.info("Elasticsearch初始化开始。。。。。");

        TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(host), port);
        transportClient.addTransportAddresses(transportAddress);
        log.info("Elasticsearch初始化完成。");
        System.out.println(transportClient.prepareSearch("dangdang")
                .setQuery(new MatchAllQueryBuilder())
                .get());
        return transportClient;
    }

}
