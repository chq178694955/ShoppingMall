package com.king.dao;

import com.king.sys.SysResource;
import com.king.sys.SysRole;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/8
 * @描述
 */
public interface SysResourcesMapper {

    List<SysResource> findResourcesByRoleId(Long roleId);

    List<SysResource> findAll();

    int add(SysResource res);

    int update(SysResource res);

    int del(Long id);

}
