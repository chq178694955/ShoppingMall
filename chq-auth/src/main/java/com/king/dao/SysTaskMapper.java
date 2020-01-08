package com.king.dao;

import com.king.sys.SysTask;

import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/26
 * @描述
 */
public interface SysTaskMapper {

    Integer init();

    List<SysTask> find(Map<String,String> params);

    SysTask get(Long taskId);

    Integer add(SysTask task);

    Integer update(SysTask task);

    Integer delete(Long taskId);

}
