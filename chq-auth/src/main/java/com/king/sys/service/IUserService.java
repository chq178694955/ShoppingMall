package com.king.sys.service;

import com.king.base.Page;
import com.king.sys.SysUser;

import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public interface IUserService {

    SysUser getUserByAccount(String account);

    /** 根据用户名 查询用户 */
    SysUser findByUserName(String username);

    /** 修改用户信息 */
    Long updateUser(SysUser user);

    Page<SysUser> find(Page<SysUser> page, Map<String,String> params);

    Long addUser(SysUser user);

    void delUser(Long id);

}
