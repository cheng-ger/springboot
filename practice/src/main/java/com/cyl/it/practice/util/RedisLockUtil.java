package com.cyl.it.practice.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
/**
 * @author chengyuanliang
 * @desc 1：java.lang.IllegalStateException
 *
 *    在返回值方面，会经常报IllegalStateException。
 *
 * RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
 *    用String类型时候，经常会报类型转换异常。我在代码中使用的Long类型接收该类型，在命令行中我们也看到命令行结果返回的是数字0或者1，保险起见我们也可以用Object对象来接收结果集。
 *
 *  2：ERR value is not an integer or out of range
 *
 *   这个问题纠结了我一个下午至少，Redis报的异常都是很深的，从跟踪源码的时候看到，我们在调用redisTemplate.execute的方法时候，如果不传序列化的参数的时候，代码默认调用的是 Jdkserializationredisserializer 来进行序列化和反序列化操作，这是jdk自带的序列化操作，使用该序列化的对象必须要实现Serializable接口。所以该序列化接口是用于对实体类的序列化。
 *
 *    所以在进行 execute 操作的时候，我们传入 Stringredisserializer，该序列化接口是专用于对字符串类型的序列化操作。具体的区别可以去这两个类的源码中看下他们的加密方式。
 *
 * @since 2019-07-17
 */

/*// 加锁
if
        redis.call('setNx',KEYS[1],ARGV[1])
    then
        if redis.call('get',KEYS[1])==ARGV[1]
    then
        return redis.call('expire',KEYS[1],ARGV[2])
    else
        return 0
    end
end



// 解锁
        if
        redis.call('get', KEYS[1]) == ARGV[1]
        then
        return redis.call('del', KEYS[1])
        else
        return 0
        end*/

@Component
public class RedisLockUtil {

    private static final Object SUCCESS = 1;
    private static final Object FAIL  = 0;
    //
    public static final String uuid = UUID.randomUUID().toString();
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean getLock(String lockKey, String value, int expireTime){
        //boolean ret = false;
        try{
            //先上Lua脚本的代码
            String script = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";

            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

            Object result = redisTemplate.execute(redisScript,new StringRedisSerializer(),new StringRedisSerializer(), Collections.singletonList(lockKey),value,expireTime + "");
            System.out.println(result + "-----------");
            //Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),value,expireTime + "");

            if(SUCCESS.equals(result)){
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 释放锁
     * @param lockKey
     * @param value
     * @return
     */
    public boolean releaseLock(String lockKey, String value){

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

        Object result = redisTemplate.execute(redisScript,new StringRedisSerializer(),new StringRedisSerializer(), Collections.singletonList(lockKey),value);
        if(SUCCESS.equals(result)) {
            return true;
        }

        return false;
    }

}
