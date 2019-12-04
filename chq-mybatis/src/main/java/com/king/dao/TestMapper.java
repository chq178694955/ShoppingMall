package com.king.dao;


import com.king.sys.TestDto;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
public interface TestMapper {

    List<TestDto> findAll();

}
