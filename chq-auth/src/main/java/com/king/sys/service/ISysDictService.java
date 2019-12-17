package com.king.sys.service;

import com.king.sys.SysDict;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/17
 * @描述
 */
public interface ISysDictService {

    List<SysDict> findDictByClass(Integer classNo);

}
