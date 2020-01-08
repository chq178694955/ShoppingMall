package com.king.sys.service;

import com.king.base.Page;
import com.king.sys.SysTask;
import org.quartz.SchedulerException;

import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/26
 * @描述
 */
public interface ISysTaskService {

    void initTaks();

    SysTask queryTaskById(Long taskId);

    Page<SysTask> queryTaskPage(Page<SysTask> page, Map<String,String> params);

    Long createTask(SysTask task);

    void modifyTask(SysTask task);

    void deleteTask(Long taskId) throws SchedulerException;

    void startTask(SysTask task) throws SchedulerException;

    void pauseTask(SysTask task) throws SchedulerException;

    void resumeTask(SysTask task) throws SchedulerException;

}
