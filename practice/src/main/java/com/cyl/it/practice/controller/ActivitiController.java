package com.cyl.it.practice.controller;

import com.cyl.it.practice.service.ActivitiService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-07-08
 */
@RestController
@RequestMapping("activitiService")
public class ActivitiController {

    @Autowired
    private ActivitiService activitiService;

    @GetMapping("start")
    public void startDemo1Process(@RequestParam String businessId) {
        activitiService.startDemo1Process(businessId);
    }

    @GetMapping("findDemo1TaskByBusinessId")
    public Task findDemo1TaskByBusinessId(@RequestParam  String businessId) {

        Task task = activitiService.findDemo1TaskByBusinessId(businessId);
        //log.info("根据businessId获取task：{}" ,task);
        return task;
    }

    @GetMapping("findDemo1TaskByBusinessIdLike")
    public List<Task> findDemo1TaskByBusinessIdLike( @RequestParam String businessId) {
        List<Task> taskList = activitiService.findDemo1TaskByBusinessIdLike(businessId);
        return taskList;
    }

    @GetMapping("completeTask")
    public void completeTask(@RequestParam String taskId) {
        activitiService.completeTask(taskId);
    }

}
