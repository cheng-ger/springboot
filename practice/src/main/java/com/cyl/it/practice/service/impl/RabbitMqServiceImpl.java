package com.cyl.it.practice.service.impl;

import com.alibaba.fastjson.JSON;
import com.cyl.it.practice.demo.UserDemo;
import com.cyl.it.practice.service.RabbitMqService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-28
 */
@Service
public class RabbitMqServiceImpl implements RabbitMqService {


    @Autowired
    private AmqpTemplate amqpTemplate;

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
}
