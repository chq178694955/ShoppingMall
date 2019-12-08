package com.king.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @创建人 chq
 * @创建时间 2019/12/8
 * @描述
 */
@Controller
@RequestMapping("sys/user")
public class UserController {

    @RequestMapping("/toMain")
    public String toMain(){
        return "sys/userMgr";
    }

}
