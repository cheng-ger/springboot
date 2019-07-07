package com.cyl.it.practice.controller;

import com.cyl.it.practice.demo.UserDemo;
import com.cyl.it.practice.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-28
 */
@RestController
@RequestMapping("cacheService")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/findUser1")
    public UserDemo findUser1() {
        return cacheService.findUser("zhangsan");
    }

    @GetMapping("/findUser2")
    public UserDemo findUser2() {
        return cacheService.findUser2("lisi");
    }

    @GetMapping("/addUser2")
    public UserDemo addUser2() {
        return cacheService.save("lisi", 2, 3);
    }

    @GetMapping("/delete")
    public void removeUser() {
        cacheService.removeUser("zhangsan");
    }


    @GetMapping("/addUser3")
    public UserDemo addUser3() {
        return cacheService.save3("wangwu", 3, 18);
    }

}
