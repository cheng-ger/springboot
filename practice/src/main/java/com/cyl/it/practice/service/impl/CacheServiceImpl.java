package com.cyl.it.practice.service.impl;

import com.cyl.it.practice.demo.UserDemo;
import com.cyl.it.practice.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-28
 */
@Service
@Slf4j
public class CacheServiceImpl implements CacheService {

    private static final String CACHE_NAME = "userCache";


    @Override
    // @Cacheable缓存key为name的数据到缓存usercache中
    @Cacheable(value = CACHE_NAME, key = "#p0")
    public UserDemo findUser(String name) {
        log.info("无缓存时执行下面代码，获取zhangsan,Time：{}", new Date().getSeconds());
        return new UserDemo(1, "zhangsan", 13);
    }

    @Override
    // @CacheEvict从缓存usercache中删除key为name的数据
    @CacheEvict(value = CACHE_NAME, key = "#p0")
    public void removeUser(String name) {
        log.info("删除数据 {}，同时清除对应的缓存", name);
    }

    @Override
    // @CachePut缓存新增的数据到缓存usercache中
    @CachePut(value = CACHE_NAME, key = "#p0")
    public UserDemo save(String name, int id, int age) {
        log.info("添加lisi用户{} ,{},{}" ,name ,id , age);
        return new UserDemo(2, "lisi", 13);
    }


    @Override
    @Cacheable(value = CACHE_NAME, key = "#p0")
    public UserDemo findUser2(String name) {
        log.info("无缓存时执行下面代码，获取lisi,Time：{}" , new Date().getSeconds() );
        return new UserDemo(2, "lisi", 13);
    }

    @Override
    @Cacheable(value = CACHE_NAME , key = "#name+'-' + #id + '-' + #age")
    public UserDemo save3(String name, int id, int age) {
        log.info("添加王五用户{} ,{},{}" ,name ,id , age);
        return new UserDemo(3, "王五", 18);
    }
}
