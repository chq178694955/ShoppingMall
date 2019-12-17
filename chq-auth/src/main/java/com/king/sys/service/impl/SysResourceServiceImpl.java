package com.king.sys.service.impl;

import com.king.dao.SysResourcesMapper;
import com.king.sys.SysResource;
import com.king.sys.service.ISysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@Service
public class SysResourceServiceImpl implements ISysResourceService {

    @Autowired
    private SysResourcesMapper sysResourcesMapper;

    @Override
    public List<SysResource> getResourceByRoleId(Long roleId) {
        return sysResourcesMapper.findResourcesByRoleId(roleId);
    }

    @Override
    public List<SysResource> findAll() {
        return sysResourcesMapper.findAll();
    }

    @Override
    public Long addResource(SysResource res) {
        sysResourcesMapper.add(res);
        return res.getId();
    }

    @Override
    public Long modifyResource(SysResource res) {
        sysResourcesMapper.update(res);
        return res.getId();
    }

    @Override
    public void delResource(Long id) {
        sysResourcesMapper.del(id);
    }
}
