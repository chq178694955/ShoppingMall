package com.king.dao;

import com.king.sys.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
public interface SysUserMapper {

    SysUser findByUserName(String username);

    int modifySysUser(SysUser user);

    int addSysUser(SysUser user);

    /** 查询 **/
    List<SysUser> find(Map<String,String> params);
    Long findCount(Map<String,String> params);

    void delUser(Long id);

    int addUserRole(SysUser user);

    int delUserRole(Long userId);

    List<SysUser> findUserRole(Long userId);

}
