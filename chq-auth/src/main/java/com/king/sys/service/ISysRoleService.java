package com.king.sys.service;

import com.king.sys.SysRole;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public interface ISysRoleService {

    List<SysRole> getRolesByUserId(Long userId);

}
