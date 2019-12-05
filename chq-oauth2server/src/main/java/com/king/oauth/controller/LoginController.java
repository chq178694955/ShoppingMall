package com.king.oauth.controller;

import com.king.sys.service.IUserService;
import com.king.utils.I18nUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
@Controller
public class LoginController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value="/login",method = RequestMethod.GET)
    public ModelAndView toLogin(Model model, HttpServletRequest request, @ModelAttribute("clientId") String clientId,
                                @ModelAttribute("redirect_uri") String redirect_uri,
                                @ModelAttribute("response_type") String response_type){
        ModelAndView view = new ModelAndView("login");
        view.addObject("clientId", clientId);
        view.addObject("redirect_uri",redirect_uri);
        view.addObject("response_type",response_type);//code授权方式
        return view;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam("account") String account, @RequestParam("password") String password,
                               @RequestParam("redirect_uri")String redirect_uri, @RequestParam("response_type")String response_type, @RequestParam("client_id")String client_id) {
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
            m.addObject("result", I18nUtils.get("com.king.system.login.tip.noaccount"));
            m.setViewName("redirect:/login?clientId=" + client_id + "&redirect_uri=" + redirect_uri + "&response_type="+response_type);
            return m;
        } catch (IncorrectCredentialsException e) {
            m.addObject("result", I18nUtils.get("com.king.system.login.tip.passerr"));
            m.setViewName("redirect:/login?clientId=" + client_id + "&redirect_uri=" + redirect_uri + "&response_type="+response_type);
            return m;
        }

        //shiro权限验证成功后跳转到授权链接
        m.addObject("redirect_uri",redirect_uri);
        m.addObject("client_id",client_id);
        m.addObject("response_type",response_type);
        m.setViewName("redirect:/oauth-server/authorize");
        return m;
    }

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String toIndex2(){
        return "index";
    }


}
