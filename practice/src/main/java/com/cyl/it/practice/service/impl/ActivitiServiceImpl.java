package com.cyl.it.practice.service.impl;

import com.cyl.it.practice.service.ActivitiService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author chengyuanliang
 * @desc
 * @since 2019-07-08
 */
@Slf4j
@Service
@Transactional
public class ActivitiServiceImpl implements ActivitiService {

    private static final String DEMO1_PROCESS = "demo1";

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;


    @Override
    public void startDemo1Process(String businessId) {
        //ProcessInstance processInstance = runtimeService.startProcessInstanceById(DEMO1_PROCESS, businessId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(DEMO1_PROCESS, businessId);
        log.info("启动demo1工作流,获取流程实例processInstance ：{}" , processInstance);
    }

    @Override
    public Task findDemo1TaskByBusinessId(String businessId) {

        Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessId).singleResult();
        log.info("根据businessId获取task：{}" ,task);
        return task;
    }

    @Override
    public List<Task> findDemo1TaskByBusinessIdLike(String businessId) {
        List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKeyLikeIgnoreCase(businessId).list();
        return taskList;
    }

    @Override
    public void completeTask(String taskId) {
        taskService.complete(taskId);
    }


}
