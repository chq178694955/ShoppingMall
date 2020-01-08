package com.king.oauth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.enums.SysResourceTypeEnum;
import com.king.sys.SysResource;
import com.king.sys.SysRole;
import com.king.sys.SysUser;
import com.king.sys.service.ISysResourceService;
import com.king.sys.service.ISysRoleService;
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
import java.util.*;

/**
 * @创建人 chq
 * @创建时间 2019/12/4
 * @描述
 */
@Controller
public class LoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysResourceService sysResourceService;

    /**
     * 登录页
     * @param request
     * @return
     */
    @RequestMapping("/toLoginAdmin")
    public ModelAndView toLoginAdmin(HttpServletRequest request){
        ModelAndView view = new ModelAndView();
        view.setViewName("loginAdmin");
        return view;
    }

    /**
     * 登录
     * @param account
     * @param password
     * @return
     */
    @RequestMapping(value = "/loginAdmin", method = RequestMethod.POST)
    public ModelAndView loginAdmin(@RequestParam("account") String account, @RequestParam("password") String password) {
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
            m.setViewName("redirect:/toLoginAdmin");
            return m;
        } catch (IncorrectCredentialsException e) {
            m.addObject("result", I18nUtils.get("com.king.system.login.tip.passerr"));
            m.setViewName("redirect:/toLoginAdmin");
            return m;
        }

        //管理员登录，进入管理平台
        m.setViewName("redirect:/index");
        return m;
    }

    @RequestMapping(value="/index",method = RequestMethod.GET)
    public String toIndex2(Model model){
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        String account = (String )subject.getPrincipal();
        //到数据库里查询要授权的内容
        SysUser user = userService.getUserByAccount(account);
        if(user != null){
            model.addAttribute("king_user_info",user);
            List<SysRole> roles = sysRoleService.getRolesByUserId(user.getId());
            Map<Long,Object> topMap = new LinkedHashMap<>();
            Map<Long,Object> childMap = new LinkedHashMap<>();
            //去重之后的所有菜单
            List<SysResource> topMenus = new ArrayList<>();
            List<SysResource> childMenus = new ArrayList<>();
            for(SysRole role : roles){
                List<SysResource> resources = sysResourceService.getResourceByRoleId(role.getId());
                for(SysResource res : resources){
                    if(res.getType() != SysResourceTypeEnum.MENU.getValue())continue;
                    //循环添加顶级节点
                    if(res.getPid() == -1){
                        if(!topMap.containsKey(res.getId())){
                            topMap.put(res.getId(),null);
                            topMenus.add(res);
                        }
                    }else{
                        if(!childMap.containsKey(res.getId())){
                            childMap.put(res.getId(),null);
                            childMenus.add(res);
                        }
                    }
                }
            }
            //循环顶级节点，将其子节点拼装成树形结构
            JSONArray roots = new JSONArray();
            for(SysResource res : topMenus){
                JSONObject node = new JSONObject();
                node.put("id",res.getId());
                node.put("text",res.getName());
                node.put("pid",res.getPid());
                node.put("url",res.getUrl());
                node.put("type",res.getType());
                node.put("iconCls","");//这里可以扩展，自定义图标样式
                JSONArray childs = hasChildrenNodes(res,childMenus,res.getId());
                node.put("children",childs);
                roots.add(node);
            }
            model.addAttribute("menuRoot",roots);
        }
        return "index";
    }

    /**
     * 递归成树
     * @param parent
     * @param children
     * @return
     */
    private JSONArray hasChildrenNodes(SysResource parent,List<SysResource> children,Long rootId){
        JSONArray datas = new JSONArray();
        for(SysResource res : children){
            if(res.getPid() == parent.getId()){
                JSONObject node = new JSONObject();
                node.put("rootId",rootId);//存一个顶级节点ID
                node.put("id",res.getId());
                node.put("text",res.getName());
                node.put("pid",res.getPid());
                node.put("url",res.getUrl());
                node.put("type",res.getType());
                node.put("iconCls","");//这里可以扩展，自定义图标样式
                JSONArray childs = hasChildrenNodes(res,children,rootId);
                node.put("children",childs);
                datas.add(node);
            }
        }
        return datas;
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "loginAdmin";
    }

}
