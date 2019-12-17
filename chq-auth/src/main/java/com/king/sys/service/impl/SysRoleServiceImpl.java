package com.king.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.king.base.Page;
import com.king.dao.SysRoleMapper;
import com.king.sys.SysRole;
import com.king.sys.SysUser;
import com.king.sys.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        return sysRoleMapper.findRolesByUserId(userId);
    }

    @Override
    public Page<SysRole> find(Page<SysRole> page, Map<String, String> params) {
        //分页设置放在查询之前
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<SysRole> list = sysRoleMapper.find(params);
        Integer totalCount = sysRoleMapper.findCount(params);
        page.setResults(list,totalCount);
        return page;
    }

    @Override
    public List<SysRole> getAll() {
        return sysRoleMapper.find(null);
    }

    @Override
    public Long addRole(SysRole role) {
        sysRoleMapper.add(role);
        if(role.getId() > 0 && role.getResourceIds().size() > 0){
            Map<String,Object> params = new HashMap<>();
            params.put("roleId",role.getId());
            params.put("resourceIds",role.getResourceIds());
            sysRoleMapper.delRoleResources(role.getId());
            sysRoleMapper.insertRoleResources(params);
        }
        return role.getId();
    }

    @Override
    public Long modifyRole(SysRole role) {
        sysRoleMapper.update(role);
        if(role.getId() > 0 && role.getResourceIds().size() > 0){
            Map<String,Object> params = new HashMap<>();
            params.put("roleId",role.getId());
            params.put("resourceIds",role.getResourceIds());
            sysRoleMapper.delRoleResources(role.getId());
            sysRoleMapper.insertRoleResources(params);
        }
        return role.getId();
    }

    @Override
    public void delRole(Long roleId) {
        sysRoleMapper.del(roleId);
    }
}
