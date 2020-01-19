package com.king.oauth.game.controller;

import com.king.game.service.IDoubleColorBallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2020/1/8
 * @描述
 */
@Controller
@RequestMapping("game/doubleColorBall")
public class DoubleColorBallController {

    @Autowired
    private IDoubleColorBallService doubleColorBallService;

    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request, Long menuId, Model model){
        model.addAttribute("menuId",menuId);
        return "game/doubleColorBallMgr";
    }

    @ResponseBody
    @RequestMapping("/genBall")
    public Object getBall(){
        Map<String, List<String>> map = doubleColorBallService.getOne();
        return map;
    }

}
