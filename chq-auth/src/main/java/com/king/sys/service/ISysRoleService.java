package com.king.sys.service;

import com.king.base.Page;
import com.king.sys.SysRole;

import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public interface ISysRoleService {

    List<SysRole> getRolesByUserId(Long userId);

    Page<SysRole> find(Page<SysRole> page, Map<String,String> params);

    List<SysRole> getAll();

    Long addRole(SysRole role);

    Long modifyRole(SysRole role);

    void delRole(Long roleId);

}
