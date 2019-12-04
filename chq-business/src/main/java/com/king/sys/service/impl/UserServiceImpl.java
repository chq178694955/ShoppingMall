package com.king.sys.service.impl;

import com.king.dao.SysUserMapper;
import com.king.sys.SysUser;
import com.king.sys.service.IUserService;
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
        SysUser user = new SysUser();
        user.setId(1l);
        user.setUsername("caohaoquan");
        user.setName("曹浩权");
        user.setPassword(this.encrypt("123456"));
        return user;
    }

    //将传进来的密码进行加密的方法
    private String encrypt(String data){
        String sha384Hex=new Sha384Hash(data).toBase64();
        return sha384Hex;
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
