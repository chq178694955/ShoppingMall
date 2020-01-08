package com.king.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.king.base.BaseJob;
import com.king.base.Page;
import com.king.dao.SysTaskLogMapper;
import com.king.dao.SysTaskMapper;
import com.king.enums.TaskEnum;
import com.king.sys.SysTask;
import com.king.sys.service.ISysTaskService;
import com.king.utils.DateUtils;
import com.king.utils.SpringUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/26
 * @描述
 */
@Service
public class SysTaskServiceImpl implements ISysTaskService {

    private Logger logger = LoggerFactory.getLogger(SysTaskServiceImpl.class);

    @Autowired
    private SysTaskMapper sysTaskMapper;

    @Autowired
    private SysTaskLogMapper sysTaskLogMapper;

    @Autowired
    private Scheduler kingScheduler;

    @Override
    public void initTaks() {
        sysTaskMapper.init();
    }

    @Override
    public SysTask queryTaskById(Long taskId) {
        return sysTaskMapper.get(taskId);
    }

    @Override
    public Page<SysTask> queryTaskPage(Page<SysTask> page, Map<String, String> params) {
        //分页设置放在查询之前
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<SysTask> list = sysTaskMapper.find(params);
        PageInfo<SysTask> pageInfo = new PageInfo<SysTask>(list);
        page.setResults(list,pageInfo.getTotal());
        return page;
    }

    @Override
    public Long createTask(SysTask task) {
        sysTaskMapper.add(task);
        return task.getId();
    }

    @Override
    public void startTask(SysTask task) throws SchedulerException {
        BaseJob job = (BaseJob)SpringUtil.getBean(task.getName());
        job = convertJob(job,task);
        /** 创建JobDetail实例,绑定Job实现类
         * JobDetail 表示一个具体的可执行的调度程序,job是这个可执行调度程序所要执行的内容
         * 另外JobDetail还包含了这个任务调度的方案和策略**/
        // 指明job的名称，所在组的名称，以及绑定job类
        JobDetail jobDetail = JobBuilder.newJob(job.getBeanClass())
                .withIdentity(job.getJobKey())
                .withDescription(job.getDescription())
                .usingJobData(job.getDataMap())
                .build();

        /**
         * Trigger代表一个调度参数的配置,什么时候去调度
         */
        //定义调度触发规则, 使用cronTrigger规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(job.getJobName(),job.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
                .startNow()
                .build();
        //将任务和触发器注册到任务调度中去
        kingScheduler.scheduleJob(jobDetail,trigger);

        //判断调度器是否启动
        if(!kingScheduler.isStarted()){
            kingScheduler.start();
        }
        logger.info(String.format("定时任务:%s.%s-已添加到调度器!", job.getJobGroup(),job.getJobName()));

        task.setLastTime(DateUtils.getTimestamp()/1000);
        sysTaskMapper.update(task);
    }

    @Override
    public void deleteTask(Long taskId) throws SchedulerException {
        SysTask task = sysTaskMapper.get(taskId);
        if(task != null){
            BaseJob job = (BaseJob)SpringUtil.getBean(task.getName());
            job = convertJob(job,task);
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(),job.getJobGroup());
            kingScheduler.pauseTrigger(triggerKey); //先暂停
            kingScheduler.unscheduleJob(triggerKey); //取消调度
            kingScheduler.deleteJob(JobKey.jobKey(job.getJobName(),job.getJobGroup())); //删除任务
            sysTaskMapper.delete(taskId);
        }
    }

    @Override
    public void pauseTask(SysTask task) throws SchedulerException {
        BaseJob job = (BaseJob)SpringUtil.getBean(task.getName());
        job = convertJob(job,task);
        kingScheduler.pauseJob(JobKey.jobKey(job.getJobName(),job.getJobGroup()));
        sysTaskMapper.update(task);
    }

    @Override
    public void resumeTask(SysTask task) throws SchedulerException {
        BaseJob job = (BaseJob)SpringUtil.getBean(task.getName());
        job = convertJob(job,task);
        kingScheduler.pauseJob(JobKey.jobKey(job.getJobName(),job.getJobGroup()));
        sysTaskMapper.update(task);
    }

    @Override
    public void modifyTask(SysTask task) {
        sysTaskMapper.update(task);
//        Boolean flag = false;
//        int resRow = sysTaskMapper.update(task);
//        if(resRow > 0){
//            BaseJob job = (BaseJob)SpringUtil.getBean(task.getName());
//
//            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
//            // 表达式调度构建器
//            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpress());
//
//            CronTrigger trigger = null;
//            try {
//                trigger = (CronTrigger) kingScheduler.getTrigger(triggerKey);
//                // 按新的cronExpression表达式重新构建trigger
//                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withDescription(task.getDescription()).withSchedule(scheduleBuilder).build();
//                if(trigger == null){
//                    //throw new TaskJobException(new ResponseResult(ExceptionEnum.TASK_ERROR.ordinal(),ExceptionEnum.TASK_ERROR.toString()));
//                }else{
//                    if(task.getCurrentState() == TaskEnum.START.getValue()){
//                        // 按新的trigger重新设置job执行
//                        kingScheduler.rescheduleJob(triggerKey, trigger);
//                    }
//                }
//                flag = true;
//            } catch (SchedulerException e) {
//                e.printStackTrace();
//            }
//        }
//        return flag;
    }

    private BaseJob convertJob(BaseJob job,SysTask task){
        job.setJobName(task.getName());
        job.setJobGroup(task.getGroupName());
        job.setCronExpression(task.getCronExpress());
        job.setJobStatus(task.getCurrentState()+"");
        job.setDescription(task.getDescription());
        return job;
    }

}
