package com.cyl.it.practice.service.impl;

import com.alibaba.fastjson.JSON;

import com.cyl.it.practice.demo.UserDemo;
import com.cyl.it.practice.service.RabbitMqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-28
 */
@Service
@Slf4j
public class RabbitMqServiceImpl implements RabbitMqService {


    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;



    @Override
    public String simpleMsg(String msg) {

        amqpTemplate.convertAndSend("simpleMsg" , msg);

        return msg;
    }

    @Override
    public UserDemo userMsg() {

       UserDemo userDemoA=  new UserDemo(2, "lisi", 13);
        amqpTemplate.convertAndSend("userChange","userDemo",userDemoA);
        return userDemoA;
    }

    @Override
    public void userDemoListMsg() {
        List<UserDemo>  userDemoList=new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            UserDemo userDemoV=  new UserDemo((int)Math.random()*10, "cyl", (int)Math.random()*100);
            userDemoList.add(userDemoV);
        }

        amqpTemplate.convertAndSend("userChange","userDemoList", JSON.toJSON(userDemoList));
    }

    @Override
    public void aTest(String msg) {
        // 设置RabbitTemplate每次发送消息都会回调这个方法
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause)
                -> log.info("confirm callback id:{},ack:{},cause:{}", correlationData, ack, cause));
        //发送时需要给出唯一的标识(CorrelationData):
        rabbitTemplate.convertAndSend("DEFAULT_EXCHANGE_A", "DEFAULT_KEY_A",
                 msg,
                new CorrelationData(UUID.randomUUID().toString()));

       // 还有一个参数需要说下：mandatory。这个参数为true表示如果发送消息到了RabbitMq，没有对应该消息的队列。
        // 那么会将消息返回给生产者，此时仍然会发送ack确认消息。

        //设置RabbitTemplate的回调如下：
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey)
                -> log.info("return callback message：{},code:{},text:{}", message, replyCode, replyText));
    }
}
