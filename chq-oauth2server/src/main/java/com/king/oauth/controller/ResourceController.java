package com.king.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @创建人 chq
 * @创建时间 2019/12/8
 * @描述
 */
@Controller
@RequestMapping("sys/resource")
public class ResourceController {

    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request, Long menuId, Model model){
        model.addAttribute("menuId",menuId);
        return "sys/resourceMgr";
    }

}
