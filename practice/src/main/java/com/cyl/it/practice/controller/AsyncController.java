package com.cyl.it.practice.controller;

import com.cyl.it.practice.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-24
 */
@RestController
@RequestMapping("asyncService")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @GetMapping("asyncTest")
    public  String asyncTest() throws InterruptedException {

        Long begin = System.currentTimeMillis();
        Future<String> asyncTaskOne = asyncService.AsyncTaskOne();
        Future<String> asyncTaskTwo = asyncService.AsyncTaskTwo();
        Future<String> asynctaskthree =  asyncService.AsyncTaskThree();

       // if()

        Long end = System.currentTimeMillis();

        System.out.println("总时间：" + (end - begin));
        return "OK";
    }


    @GetMapping("asyncTest2")
    public  String asyncTest2() throws InterruptedException {

        Long begin = System.currentTimeMillis();
        Future<String> asyncTaskOne = asyncService.AsyncTaskOne();
        Future<String> asyncTaskTwo = asyncService.AsyncTaskTwo();
        Future<String> asynctaskThree =  asyncService.AsyncTaskThree();

        for ( ; ; ) {
           if(asyncTaskOne.isDone() && asyncTaskTwo.isDone() && asynctaskThree.isDone()) break;

        }
        Long end = System.currentTimeMillis();

        System.out.println("总时间：" + (end - begin));
        return "OK";
    }


    @GetMapping("asyncTest3")
    public  String asyncTest3()  {

        Long begin = System.currentTimeMillis();

        String mainThread = Thread.currentThread().getName();

        String threadOne = asyncService.threadOne();

        String threadTwo = asyncService.threadTwo();


        Long end = System.currentTimeMillis();

        System.out.println(mainThread + "总时间：" + (end - begin));

        return "OK";
    }


    @GetMapping("asyncTest4")
    public  String asyncTest4()  {

        Long begin = System.currentTimeMillis();

        String mainThread = Thread.currentThread().getName();

        String threadOne = asyncService.threadOne();

        String threadTwo = asyncService.threadTwo();



        for ( ; ; ) {
            if("OK".equals(threadOne) && "OK".equals(threadTwo)) break;

        }

        Long end = System.currentTimeMillis();

        System.out.println(mainThread + "总时间：" + (end - begin));
        return "OK";
    }

}
