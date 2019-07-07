package com.cyl.it.practice.service;

import java.util.concurrent.Future;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-06-24
 */
public interface AsyncService {

     Future<String> AsyncTaskOne() throws InterruptedException;
     Future<String> AsyncTaskTwo() throws InterruptedException;
     Future<String> AsyncTaskThree() throws InterruptedException;


    String threadOne() ;
    String threadTwo();







}
