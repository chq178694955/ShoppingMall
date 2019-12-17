package com.king.oauth.exception;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public class MissServletRequestParameterException extends Exception {

    private static final long serialVersionUID = 1L;

    public MissServletRequestParameterException(String msg){
        super(msg);
    }
}
