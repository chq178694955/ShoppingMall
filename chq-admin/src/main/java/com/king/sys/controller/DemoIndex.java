package com.king.sys.controller;

import com.king.exception.ExceptionEnum;
import com.king.exception.NoPermissionException;
import com.king.response.ResponseResult;
import com.king.utils.I18nUtils;
import com.king.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@RestController
public class DemoIndex {

    protected static Logger logger = LoggerFactory.getLogger(DemoIndex.class);

    @RequestMapping("/hello")
    public ResponseResult hello(@RequestParam(value="role",required = false)Integer role)throws Exception{
        logger.info("开始访问controller");
        String msg = I18nUtils.get("com.king.system.name");
        String msg2 = I18nUtils.get("welcome");
        System.out.println(msg);
        System.out.println(msg2);
        StringUtils.sayHello();
        Integer i = role;
//        int c = 1/0;
        if(i < 0){
            throw new NoPermissionException(ExceptionEnum.WARN_PERMISSIONS.toString());
        }else{
            return new ResponseResult("爱我中华");
        }
    }

}
