package com.king.sys.service.impl;

import com.king.sys.SysResource;
import com.king.sys.service.ISysResourceService;
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
    @Override
    public List<SysResource> getResourceByRoleId(Long roleId) {
        List<SysResource> resources = new ArrayList<>();
        SysResource res = new SysResource();
        res.setId(1l);
        res.setName("模块1");
        res.setPermission("MOD1");
        resources.add(res);
        return resources;
    }
}
