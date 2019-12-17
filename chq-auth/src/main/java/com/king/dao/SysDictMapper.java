package com.king.dao;

import com.king.sys.SysDict;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/17
 * @描述
 */
public interface SysDictMapper {

    List<SysDict> findDictByClass(Integer classNo);

    List<SysDict> findDictByParent(Long parentSn);

    List<SysDict> findById(Long dictSn);

}
