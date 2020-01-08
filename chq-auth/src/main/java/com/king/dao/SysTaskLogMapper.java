package com.king.dao;

import com.king.sys.SysTaskLog;

import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/26
 * @描述
 */
public interface SysTaskLogMapper {

    List<SysTaskLog> find(Map<String, String> params);

    Integer add(SysTaskLog taskLog);

    Integer delete(Long taskLogId);

    Integer deleteByTaskId(Long taskId);

}
