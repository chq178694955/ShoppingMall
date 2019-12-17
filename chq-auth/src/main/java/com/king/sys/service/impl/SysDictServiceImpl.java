package com.king.sys.service.impl;

import com.king.dao.SysDictMapper;
import com.king.sys.SysDict;
import com.king.sys.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/17
 * @描述
 */
@Service
public class SysDictServiceImpl implements ISysDictService {
    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDict> findDictByClass(Integer classNo) {
        return sysDictMapper.findDictByClass(classNo);
    }
}
