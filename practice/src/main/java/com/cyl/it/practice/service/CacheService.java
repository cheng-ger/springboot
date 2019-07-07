package com.cyl.it.practice.service;

import com.cyl.it.practice.demo.UserDemo;

/**
 * @author chengyuanliang
 * @desc
 * @CacheConfig(cacheNames="users") 注解指的是该类中的缓存的名称都是users
 * @CachePut(key=" 'userCache' ")中userCache要加‘’单引号，表示这是一个字符串。
 *
 * @Cacheable能够根据方法的请求参数对其结果进行缓存(缓存的是方法的返回结果)，一般用于insert()操作
 *
 * @CachePut(key="'userCache'")主要针对方法配置，能够根据方法的请求参数对其结果进行缓存，和
 * @Cacheable 不同的是，它每次都会触发真实方法的调用，一般用于update()操作
 *
 * @CacheEvict(key="'userCache'")主要针对方法配置，能够根据一定的条件对缓存进行清空，一般用于delete（）操作
 * 本例中的@Cacheable和@CachePut和@CacheEvict的key值必须都是同一个缓存的key，因为这样当update的时候缓存的时候，get方法的得到的才是最新数据，而当删除的时候@CacheEvict，也必须把该key的缓存删除。
 * @since 2019-06-25
 */
public interface CacheService {

    UserDemo findUser(String name);

    void removeUser(String name);

    UserDemo save(String name, int id, int age);

    UserDemo findUser2(String name);

    UserDemo save3(String name, int id, int age);

}
