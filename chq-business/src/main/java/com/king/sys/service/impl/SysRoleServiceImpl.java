package com.king.sys.service.impl;

import com.king.sys.SysRole;
import com.king.sys.service.ISysRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        List<SysRole> roles = new ArrayList<>();
        SysRole role = new SysRole();
        role.setId(1l);
        role.setName("admin");
        roles.add(role);
        return roles;
    }

}
