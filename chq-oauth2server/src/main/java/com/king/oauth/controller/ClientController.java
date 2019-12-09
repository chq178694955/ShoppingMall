package com.king.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @创建人 chq
 * @创建时间 2019/12/9
 * @描述
 */
@Controller
@RequestMapping("sys/client")
public class ClientController {

    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request){
        return "sys/clientMgr";
    }

}
