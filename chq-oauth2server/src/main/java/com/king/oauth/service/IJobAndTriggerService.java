package com.king.oauth.service;

import com.king.base.Page;
import com.king.oauth.entity.JobAndTrigger;

import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/25
 * @描述
 */
public interface IJobAndTriggerService {

    Page<JobAndTrigger> queryJob(Page<JobAndTrigger> page, Map<String,String> params);

}
