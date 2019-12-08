package com.king.sys.service.impl;

import com.king.dao.SysUserMapper;
import com.king.sys.SysUser;
import com.king.sys.service.IUserService;
import com.king.util.PasswordUtil;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int updateUser(SysUser user) {
        return sysUserMapper.modifySysUser(user);
    }
}
