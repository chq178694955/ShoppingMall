package com.king.dao;

import com.king.sys.SysUser;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
public interface SysUserMapper {

    SysUser findByUserName(String username);

    int modifySysUser(SysUser user);

}
