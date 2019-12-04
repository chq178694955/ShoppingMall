package com.king.sys.service;

import com.king.sys.dto.SysUser;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public interface IUserService {

    SysUser getUserByAccount(String account);

}
