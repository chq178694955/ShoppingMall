package com.king.sys.service.impl;

import com.king.sys.dto.SysUser;
import com.king.sys.service.IUserService;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.springframework.stereotype.Service;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public SysUser getUserByAccount(String account) {
        SysUser user = new SysUser();
        user.setId(1l);
        user.setAccount("caohaoquan");
        user.setName("曹浩权");
        user.setPwd(this.encrypt("123456"));
        return user;
    }

    //将传进来的密码进行加密的方法
    private String encrypt(String data){
        String sha384Hex=new Sha384Hash(data).toBase64();
        return sha384Hex;
    }
}
