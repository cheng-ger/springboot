package com.cyl.it.practice.service;

import com.cyl.it.practice.vo.VacationVO;
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


    //换一个vacation的流程
    List<String> approvalVacationUserName();

    //开始一个vacation流程 apply人提交
    Object startVac(VacationVO vac);

    //查询我发起的流程
    Object startMyVacByMe(String userName);

    //根据用户查询待办任务列表
    Object myVac(String userName);

    //查询已完成任务列表
    Object myFinishTaskVac(String userName);


    //审批vacation approval
    Object approvalVacation(String taskId, String userName, String msg);

    //根据流程Id 获取其执行到哪里或历史
    Object queryProcessByInstanceId(String processInstanceId);
}
