package com.king.sys.service;

import com.king.sys.SysUser;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public interface IUserService {

    SysUser getUserByAccount(String account);

    /** 根据用户名 查询用户 */
    public SysUser findByUserName(String username);

    /** 修改用户信息 */
    public int updateUser(SysUser user);

}
