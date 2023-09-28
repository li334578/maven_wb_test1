package com.example.wb_test1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "rocketmq.producer")
@Configuration
@Data
public class ProducerConfig {

    /**
     * 生产者组信息
     */
    private String groupName;

    /**
     * rocket mq注册中心
     */
    private String namesrvAddr;

    /**
     * topic
     */
    private String topicName;

    /**
     * tag
     */
    private String[] tag;

    /**
     * key
     */
    private String key;
}