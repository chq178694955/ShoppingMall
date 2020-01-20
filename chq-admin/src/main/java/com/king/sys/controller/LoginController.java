package com.king.sys.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
@Controller
public class LoginController {

    @RequestMapping("/")
    public String toIndex(){
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal().toString());
        return "index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/welcome1")
    public String welcome1(){
        return "welcome1";
    }

    @RequestMapping("/oauth2Failure")
    public String fail(){
        return "oauth2Failure";
    }

}
