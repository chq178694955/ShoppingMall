package com.king.oauth.exception;

import com.king.oauth.response.ResponseResult;

/**
 * @创建人 chq
 * @创建时间 2019/12/25
 * @描述
 */
public class TaskJobException extends RuntimeException {

    private int code;

    public TaskJobException(ResponseResult response){
        super(response.getMsg());
        this.code = response.getCode();
    }

}
