package com.king.dao;

import com.king.sys.SysRole;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/8
 * @描述
 */
public interface SysRoleMapper {

    SysRole findByRoleId(Long roleId);

    List<SysRole> findRolesByUserId(Long userId);

}
