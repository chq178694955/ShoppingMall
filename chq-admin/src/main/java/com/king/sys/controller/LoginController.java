package com.king.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
@Controller
public class LoginController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/oauth2Failure")
    public String fail(){
        return "oauth2Failure";
    }

}
