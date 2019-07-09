package com.cyl.it.practice.service;

import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-07-08
 */
public interface ActivitiService {

    /*
     * @author chengyuanliang
     * @since 2019/7/8 21:24
     * @desc   启动流程
     * @param  [businessId : 与业务Id关联起来]
     * @return void
     **/
    void startDemo1Process(String businessId);

    // 通过业务key查询出对应的任务businessId
    Task findDemo1TaskByBusinessId(String businessId);


    // 通过业务key模糊查询出对应的任务 businessId
    List<Task> findDemo1TaskByBusinessIdLike(String businessId);

    //审批
    void completeTask(String taskId );
}
