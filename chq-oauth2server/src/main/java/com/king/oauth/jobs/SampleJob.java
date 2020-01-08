package com.king.oauth.jobs;

import com.king.base.BaseJob;
import com.king.utils.DateUtils;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义任务
 * @创建人 chq
 * @创建时间 2019/12/25
 * @描述
 */
@Component
public class SampleJob extends BaseJob {

    private static final long serialVersionUID = 2850496667588136450L;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date start = new Date();
        System.out.println("开始任务" + DateUtils.convert2String(start,"yyyy-MM-dd HH:mm:ss"));

        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String jobName = jobDetail.getKey().getName();
        String groupName = jobDetail.getKey().getGroup();

        // do something
        System.out.println("执行你的业务逻辑...");


        Date end = new Date();
        System.out.println("结束任务" + DateUtils.convert2String(start,"yyyy-MM-dd HH:mm:ss"));
        System.out.println("共执行时间（秒）：" + DateUtils.diffSecond(start,end));
    }

}
