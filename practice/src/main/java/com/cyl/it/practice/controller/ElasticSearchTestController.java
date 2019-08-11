package com.cyl.it.practice.controller;

import com.cyl.it.practice.dao.ItemModelDao;
import com.cyl.it.practice.dao.UserInfoModelDao;
import com.cyl.it.practice.model.ItemModel;
import com.cyl.it.practice.model.UserInfoModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-08-10
 */
@RestController
@Slf4j
@RequestMapping("elasticService")
public class ElasticSearchTestController {

    @Autowired
    private ItemModelDao itemDao;

    @Autowired
    private UserInfoModelDao userInfoDao;

    @GetMapping("itemAddOne")
    public String itemAddOne() {
        log.info("start : itemAddOne ========>>>>");
        String uuid = UUID.randomUUID().toString();
        ItemModel itemModel = new ItemModel(uuid, "小米手机7", " 手机",
                "小米", 3499.00, "http://image.baidu.com/13123.jpg");
        itemDao.save(itemModel);
        log.info("end : itemAddOne ========>>>> model:{}", itemModel);
        return "success";
    }

    @GetMapping("userInfoAddOne")
    public  String userInfoAddOne() {
        log.info("start : userInfoAddOne ========>>>>");
        String uuid = UUID.randomUUID().toString();
        UserInfoModel userInfoModel = new UserInfoModel(uuid, "cyl" + (int) Math.random()*1000 , 18,
                "1", "go fish");
        userInfoDao.save(userInfoModel);
        log.info("end : userInfoAddOne ========>>>> model:{}" , userInfoModel);
        return "success";
    }




}
