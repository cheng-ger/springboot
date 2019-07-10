package com.cyl.it.practice.controller;

import com.cyl.it.practice.service.ActivitiService;
import com.cyl.it.practice.vo.VacationVO;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("startVacation")
    public Object startVac(@RequestBody VacationVO vac){
          return activitiService.startVac(vac);
    }

    @GetMapping("myVac")
    public Object myVac(@RequestParam String userName) {
        return activitiService.myVac(userName);
    }

    @GetMapping("approvalVacation")
    public Object approvalVacation(@RequestParam String taskId, @RequestParam String userName,@RequestParam  String msg){
        return activitiService.approvalVacation(taskId, userName, msg);
    }

    @GetMapping("myFinishTaskVac")
    public Object myFinishTaskVac(@RequestParam String userName) {
        return activitiService.myFinishTaskVac(userName);
    }

    @GetMapping("startMyVacByMe")
    public Object startMyVacByMe(@RequestParam String userName){
        return activitiService.startMyVacByMe(userName);
    }

    @GetMapping("queryProcess")
    public Object queryProcessByInstanceId(@RequestParam String processInstanceId) {
        return activitiService.queryProcessByInstanceId(processInstanceId);
    }

}
