package com.king.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.king.base.BaseJob;
import com.king.base.Page;
import com.king.sys.SysTask;
import com.king.sys.service.ISysTaskService;
import com.king.utils.*;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/26
 * @描述
 */
@Controller
@RequestMapping("/sys/task")
public class TaskController extends BaseController{

    // 10/0 * * * * ?

    @Autowired
    private ISysTaskService sysTaskService;

    @Autowired
    private Scheduler kingScheduler;

    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request, Long menuId, Model model){
        model.addAttribute("menuId",menuId);
        return "sys/taskMgr";
    }

    @ResponseBody
    @RequestMapping("/query")
    public Object query(HttpServletRequest request){
        Page<SysTask> page = this.getPage(request);
        Map<String,String> params = new HashMap<>();

        page = sysTaskService.queryTaskPage(page,params);
        return this.getGridData(page);
    }

    @ResponseBody
    @RequestMapping("/save")
    public Object save(HttpServletRequest request,Long id, String name,String groupName,String express,Integer currentState,Integer defaultState,String description){
        SysTask task = new SysTask();
        if(!StringUtils.isEmpty(name)){
            task.setName(name);
        }
        if(!StringUtils.isEmpty(groupName)){
            task.setGroupName(groupName);
        }
        if(!StringUtils.isEmpty(express)){
            task.setCronExpress(express);
        }
        if(!StringUtils.isEmpty(description)){
            task.setDescription(description);
        }
        if(!StringUtils.isEmpty(currentState)){
            task.setCurrentState(currentState);
        }
        if(!StringUtils.isEmpty(defaultState)){
            task.setDefaultState(defaultState);
        }
        task.setLastTime(DateUtils.getTimestamp()/1000);
        JSONObject result = new JSONObject();
        Long resId = 0l;
        if(StringUtils.isEmpty(id)){
            resId = sysTaskService.createTask(task);
        }
        if(resId > 0){
            result.put("code",0);
            result.put("msg", I18nUtils.get("com.king.system.operation.ok"));
        }else{
            result.put("code",-1);
            result.put("msg", I18nUtils.get("com.king.system.operation.fail"));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/start")
    public Object start(Long taskId,Integer taskState) throws SchedulerException {
        SysTask task = sysTaskService.queryTaskById(taskId);
        task.setCurrentState(taskState);
        JSONObject result = new JSONObject();
        sysTaskService.startTask(task);
        result.put("code",0);
        result.put("msg", I18nUtils.get("com.king.system.operation.ok"));

        return result;
    }

    @ResponseBody
    @RequestMapping("/pause")
    public Object pauseTask(Long taskId,Integer taskState) throws SchedulerException {
        SysTask task = sysTaskService.queryTaskById(taskId);
        task.setCurrentState(taskState);

        BaseJob job = (BaseJob)SpringUtil.getBean(task.getName());
        job = convertJob(job,task);
        kingScheduler.pauseJob(JobKey.jobKey(job.getJobName(),job.getJobGroup()));

        sysTaskService.modifyTask(task);
        JSONObject result = new JSONObject();
        result.put("code",0);
        result.put("msg", I18nUtils.get("com.king.system.operation.ok"));
        return result;
    }

    @ResponseBody
    @RequestMapping("/resume")
    public Object resumeTask(Long taskId,Integer taskState) throws SchedulerException {
        SysTask task = sysTaskService.queryTaskById(taskId);
        task.setCurrentState(taskState);

        BaseJob job = (BaseJob)SpringUtil.getBean(task.getName());
        job = convertJob(job,task);
        kingScheduler.resumeJob(JobKey.jobKey(job.getJobName(),job.getJobGroup()));

        sysTaskService.modifyTask(task);
        JSONObject result = new JSONObject();
        result.put("code",0);
        result.put("msg", I18nUtils.get("com.king.system.operation.ok"));
        return result;
    }

    @ResponseBody
    @RequestMapping("/finish")
    public Object finishTask(Long taskId,Integer taskState) throws SchedulerException {
        JSONObject result = new JSONObject();
        sysTaskService.deleteTask(taskId);
        result.put("code",0);
        result.put("msg", I18nUtils.get("com.king.system.operation.ok"));
        return result;
    }

    private BaseJob convertJob(BaseJob job, SysTask task){
        job.setJobName(task.getName());
        job.setJobGroup(task.getGroupName());
        job.setCronExpression(task.getCronExpress());
        job.setJobStatus(task.getCurrentState()+"");
        job.setDescription(task.getDescription());
        return job;
    }

}
