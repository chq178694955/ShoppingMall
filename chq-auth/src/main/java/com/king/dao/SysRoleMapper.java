package com.king.dao;

import com.king.sys.SysRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @创建人 chq
 * @创建时间 2019/12/8
 * @描述
 */
public interface SysRoleMapper {

    SysRole findByRoleId(Long roleId);

    List<SysRole> findRolesByUserId(Long userId);

    /** 查询 **/
    List<SysRole> find(Map<String,String> params);
    Integer findCount(Map<String,String> params);

    Integer add(SysRole role);

    Integer update(SysRole role);

    Integer del(Long roleId);

    void insertRoleResources(Map<String,Object> params);

    void delRoleResources(Long roleId);

}
