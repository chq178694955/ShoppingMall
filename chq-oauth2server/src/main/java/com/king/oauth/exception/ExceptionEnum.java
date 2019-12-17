package com.king.oauth.exception;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public enum ExceptionEnum {

    WARN_PARAMETER("参数错误"),
    WARN_PERMISSIONS("暂无权限"),
    WARN_SERVER("服务器错误");

    private final String value;

    String getValue(){
        return value;
    }

    ExceptionEnum(String value){
        this.value = value;
    }

    public String toString(){
        return value;
    }

}
