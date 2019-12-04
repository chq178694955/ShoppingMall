package com.king.exception;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public class NoPermissionException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoPermissionException(String msg){
        super(msg);
    }

}
