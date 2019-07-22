package com.cyl.it.practice.service;

import com.cyl.it.practice.demo.UserDemo;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-28
 */
public interface RabbitMqService {


    String simpleMsg(String msg);

    UserDemo userMsg();

    void userDemoListMsg();

    void aTest(String msg);


}
