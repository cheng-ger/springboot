package com.cyl.it.practice.service.impl;

import com.cyl.it.practice.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-24
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {


    @Override
    @Async  //可以为 class method   @Target({ElementType.TYPE, ElementType.METHOD})
    public Future<String> AsyncTaskOne() throws InterruptedException {
        Long begin = System.currentTimeMillis();
        Thread.sleep(1000);
        long end = System.currentTimeMillis();
        System.out.println("AsyncTaskOne"+ (end - begin));

        return  new AsyncResult<>("AsyncTaskOne");
    }

    @Override
    @Async
    public Future<String> AsyncTaskTwo() throws InterruptedException {
        Long begin = System.currentTimeMillis();
        Thread.sleep(2000);
        long end = System.currentTimeMillis();
        System.out.println("AsyncTaskTwo"+ (end - begin));

        return  new AsyncResult<>("AsyncTaskTwo");
    }


    @Override
    @Async
    public Future<String> AsyncTaskThree() throws InterruptedException {
        Long begin = System.currentTimeMillis();
        Thread.sleep(3000);
        long end = System.currentTimeMillis();
        System.out.println("AsyncTaskThree"+ (end - begin));

        return  new AsyncResult<>("AsyncTaskThree");
    }

    @Override
    public String threadOne()  {

        new Thread( () -> {
            long start = System.currentTimeMillis();
            Thread.currentThread().setName("threadOne");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            long end = System.currentTimeMillis();
            System.out.println("threadOne <TIME>" + Thread.currentThread().getName() + (end - start));
        }).start();


        return "OK";
    }

    @Override
    public String threadTwo() {

        new Thread( () -> {
            long start = System.currentTimeMillis();
            Thread.currentThread().setName("threadTwo");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long end = System.currentTimeMillis();

            System.out.println("threadTwo <TIME>:" + Thread.currentThread().getName() +  (end - start));
        }).start();
        return "OK";
    }


}
