package com.cyl.it.practice.service.impl;

import com.cyl.it.practice.service.ActivitiService;
import com.cyl.it.practice.vo.VacTaskVO;
import com.cyl.it.practice.vo.VacationVO;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

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
    private static final String PROCESS_VACATION = "vacation";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
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

    @Override
    public List<String> approvalVacationUserName() {

        return Arrays.asList("a" , "b" , "c" , "d");
    }

    @Override
    public Object startVac(VacationVO vac) {
        //Authentication.setAuthenticatedUserId(userId);
        identityService.setAuthenticatedUserId(vac.getApplyUser());
        // 开始流程
        ProcessInstance vacationInstance  = runtimeService.startProcessInstanceByKey(PROCESS_VACATION);
        // 查询当前任务
        Task currentTask = taskService.createTaskQuery().processInstanceId(vacationInstance.getId()).singleResult();
        log.info("vacationInstance==>processInstanceName:{}",vacationInstance.getStartUserId());
        // 申明任务
        taskService.claim(currentTask.getId(), vac.getApplyUser());

        Map<String, Object> vars = new HashMap<>(4);
        vars.put("applyUser", vac.getApplyUser());
        vars.put("days", vac.getDays());
        vars.put("reason", vac.getReason());
        vars.put("applyDate", DATE_FORMAT.format(new Date()));
        taskService.complete(currentTask.getId(), vars);

        return true;
    }

    @Override
    public Object startMyVacByMe(String userName) {
       return historyService.createHistoricProcessInstanceQuery().processDefinitionKey(PROCESS_VACATION).startedBy(userName).list();
    }

    @Override
    public Object myVac(String userName) {
        log.info("myVac==>>param:{}", userName);

        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(PROCESS_VACATION).taskCandidateOrAssigned(userName).list();
        //List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().startedBy(userName).list();
       // taskService.createTaskQuery().
        List<VacTaskVO> vacList = new ArrayList<>();
        for (Task task : taskList) {
            VacTaskVO vac = getVacTasTask(task);
            vacList.add(vac);
        }
        return vacList;
    }

    @Override
    public Object myFinishTaskVac(String userName) {
        return  historyService.createHistoricTaskInstanceQuery().taskAssignee(userName).list();
    }

    @Override
    public Object approvalVacation(String taskId, String userName, String msg) {

        taskService.claim(taskId , userName);

        Map<String, Object> vars = new HashMap<>();
        vars.put("msg", msg);

        taskService.complete(taskId , vars);


        return true;
    }

    @Override
    public Object queryProcessByInstanceId(String processInstanceId) {
        return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
    }


    private VacTaskVO getVacTasTask(Task instance) {
        String instanceId = instance.getProcessInstanceId();

        String applyUser = runtimeService.getVariable(instanceId, "applyUser", String.class);
        Integer days = runtimeService.getVariable(instanceId, "days", Integer.class);
        String reason = runtimeService.getVariable(instanceId, "reason", String.class);

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionKey(PROCESS_VACATION).processInstanceId(instanceId).singleResult();

        VacTaskVO vacTaskVO = new VacTaskVO();

        vacTaskVO.setId(instance.getId());
        vacTaskVO.setName(instance.getName());

        VacationVO vac = new VacationVO();

        vac.setApplyUser(applyUser);
        vac.setDays(days);
        vac.setReason(reason);
        vac.setProcessInstanceId(instanceId);
        vacTaskVO.setVac(vac);
        Date startTime = processInstance.getStartTime(); // activiti 6 才有
        vac.setApplyTime(praseUTCToCST(startTime));
        vac.setApplyStatus(processInstance.isEnded() ? "申请结束" : "等待审批");
        return vacTaskVO;
    }


    private Date praseUTCToCST(Date source){

        //Date parse = DATE_FORMAT.parse(source);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(source);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        return calendar.getTime();

    }







}
