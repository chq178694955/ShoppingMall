package com.king.response;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public class ResponseResult<T> {

    public final static Integer OK = 0;
    public final static String SUCCESS = "SUCCESS";
    public final static String FAILURE = "FAILURE";

    private int code;

    private String msg;

    private T data;

    public ResponseResult() {
    }

    public ResponseResult(int code){
        this.code = code;
        if(code == OK){
            this.msg = SUCCESS;
        }else{
            this.msg = FAILURE;
        }
    }

    public ResponseResult(T data){
        this.code = OK;
        this.msg = SUCCESS;
        this.data = data;
    }

    public ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
