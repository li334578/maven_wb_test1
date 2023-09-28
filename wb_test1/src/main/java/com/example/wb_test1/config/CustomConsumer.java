package com.example.wb_test1.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@Slf4j
public class CustomConsumer extends DefaultConsumerConfigure implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConsumerConfig consumerConfig;

    private static final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        try {
            // 监听Topic下的所有tag，此topic业务为日志记录
            super.listener(consumerConfig.getTopicName(), StringUtils.join(consumerConfig.getTag(), "||"));
        } catch (MQClientException e) {
            log.error("消费者监听器启动失败" , e);
        }
    }

    @Override
    public ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs) {
        if (CollUtil.isEmpty(msgs)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt message = msgs.get(0);
        String result = new String(message.getBody());
        log.info("收到消息了: ---- {}" , result);
        if (Objects.equals(result, "m8")) {
            int i = atomicInteger.incrementAndGet();
            log.info("这是第 {} 次 时间是 {}" , i, DateUtil.now());
            if (i < 3) {
                // 消息处理
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            } else {
                log.info(" {} 消费成功" , result);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}
