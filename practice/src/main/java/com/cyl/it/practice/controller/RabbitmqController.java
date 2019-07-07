package com.cyl.it.practice.controller;

import com.cyl.it.practice.service.RabbitMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-25
 */
@RestController
@RequestMapping("rabbitMqService")
public class RabbitmqController {

    @Autowired
    private RabbitMqService rabbitMqService;

    @GetMapping(value = "simpleMsg")
    public String simpleMsg(@RequestParam(value = "msg" ,required=false)  String msg){
        rabbitMqService.simpleMsg(msg);
        return msg;
    }


    @GetMapping("userDemoMsg")
    public void  userDemoMsg(){
        rabbitMqService.userMsg();
    }

    @GetMapping("userDemoListMsg")
    public void userListMsg() {
        rabbitMqService.userDemoListMsg();
    }


}
