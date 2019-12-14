package com.king.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.king.base.Page;
import com.king.dao.SysUserMapper;
import com.king.sys.SysUser;
import com.king.sys.service.IUserService;
import com.king.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser getUserByAccount(String account) {
        SysUser user = sysUserMapper.findByUserName(account);
        return user;
    }

    @Override
    public SysUser findByUserName(String username) {
        return sysUserMapper.findByUserName(username);
    }

    @Override
    public Long updateUser(SysUser user) {
        sysUserMapper.modifySysUser(user);
        if(user.getRoleId() > 0 && user.getId() > 0){
            sysUserMapper.delUserRole(user.getId());
            sysUserMapper.addUserRole(user);
        }
        return user.getId();
    }

    @Override
    public Page<SysUser> find(Page<SysUser> page, Map<String, String> params) {
        //分页设置放在查询之前
        PageHelper.startPage(page.getPageNo(), page.getPageSize());
        List<SysUser> list = sysUserMapper.find(params);
        Integer totalCount = sysUserMapper.findCount(params);
        page.setResults(list,totalCount);
        return page;
    }

    @Override
    public Long addUser(SysUser user) {
        sysUserMapper.addSysUser(user);
        if(user.getId() > 0 && !StringUtils.isEmpty(user.getRoleId())){
            sysUserMapper.addSysUser(user);
        }
        return user.getId();
    }

    @Override
    public void delUser(Long id) {
        sysUserMapper.delUserRole(id);
        sysUserMapper.delUser(id);
    }
}
