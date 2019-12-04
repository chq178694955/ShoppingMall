package com.king.exception;

import com.king.response.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常代理类
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@CrossOrigin
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseResult processException(Exception ex, HttpServletRequest request, HttpServletResponse response){
        ex.printStackTrace();
        if(ex instanceof MissServletRequestParameterException){
            return new ResponseResult(400,ExceptionEnum.WARN_PARAMETER.toString());
        }else if(ex instanceof NoPermissionException){
            logger.error("=============" + ex.getMessage() + "=================");
            return new ResponseResult(401,ExceptionEnum.WARN_PERMISSIONS.toString());
        }
        return new ResponseResult(500,ExceptionEnum.WARN_SERVER.toString());
    }
}
