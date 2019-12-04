package com.king.sys.controller;

import com.king.dao.TestMapper;
import com.king.sys.TestDto;
import com.king.sys.service.IUserService;
import com.king.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
@Controller
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private IUserService userService;

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String toLogin(){
        redisUtil.set("aabbcc","123abc");
        List<TestDto> testDtos = testMapper.findAll();
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login2(@RequestParam("account") String account, @RequestParam("password") String password) {
        ModelAndView m = new ModelAndView();
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken uToken = new UsernamePasswordToken(account, password);
        //实现记住我
        uToken.setRememberMe(true);
        try {
            //进行验证，报错返回首页，不报错到达成功页面。
            subject.login(uToken);

        } catch (UnknownAccountException e) {
            m.addObject("result", "用户不存在");
            m.setViewName("login");
            return m;
        } catch (IncorrectCredentialsException e) {
            m.addObject("result", "密码错误");
            m.setViewName("login");
            return m;
        }

        //shiro权限验证成功后跳转的界面
        m.setViewName("redirect:index");
        return m;
    }

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String toIndex(){
        return "index";
    }


}
