package com.example.wb_test1.controller;

import com.alibaba.fastjson.JSON;
import com.example.wb_test1.config.ProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Date 2023-09-28 13:50
 * @Description TODO
 * @Version 1.0.0
 * @Author liwenbo
 */
@RestController
@Slf4j
public class HelloController {


    @Resource
    private DefaultMQProducer producer;

    @Resource
    private ProducerConfig producerConfig;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return name;
    }

    @GetMapping("/send")
    public void sendOneWay(@RequestParam("message") String message) throws RemotingException, InterruptedException, MQClientException {

        Message mqMsg = new Message(producerConfig.getTopicName(), producerConfig.getTag()[0], producerConfig.getKey(), message.getBytes());

        producer.send(mqMsg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("传输成功");
                log.info(JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("传输失败" , throwable);
            }
        });
    }

}
