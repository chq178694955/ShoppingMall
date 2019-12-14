package com.king.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.king.base.Page;
import com.king.enums.AccountStateEnum;
import com.king.sys.Oauth2Client;
import com.king.sys.SysUser;
import com.king.sys.service.IUserService;
import com.king.utils.AppUtils;
import com.king.utils.I18nUtils;
import com.king.utils.MD5Util;
import com.king.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/8
 * @描述
 */
@Controller
@RequestMapping("sys/user")
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request, Long menuId, Model model){
        model.addAttribute("menuId",menuId);
        return "sys/userMgr";
    }

    @ResponseBody
    @RequestMapping("/query")
    public Object query(HttpServletRequest request){
        Page<SysUser> page = this.getPage(request);
        Map<String,String> params = new HashMap<>();
        page = userService.find(page,params);
        return this.getGridData(page);
    }

    @ResponseBody
    @RequestMapping("/save")
    public Object save(HttpServletRequest request,Long id, String username,String password,String name,String idCardNum,Integer state,Long roleId){
        SysUser user = new SysUser();
        if(!StringUtils.isEmpty(username)){
            user.setUsername(username);
        }
        if(!StringUtils.isEmpty(password)){
            user.setPassword(MD5Util.encode(password));
        }
        if(!StringUtils.isEmpty(idCardNum)){
            user.setIdCardNum(idCardNum);
        }
        if(!StringUtils.isEmpty(name)){
            user.setName(name);
        }
        if(!StringUtils.isEmpty(roleId)){
            user.setRoleId(roleId);
        }
        if(!StringUtils.isEmpty(state)){
            if(AccountStateEnum.NORMAL.getValue().equals(state)){
                user.setState(AccountStateEnum.NORMAL.getValue());
            }else{
                user.setState(AccountStateEnum.LOCK.getValue());
            }
        }

        JSONObject result = new JSONObject();
        Long resId = 0l;
        if(StringUtils.isEmpty(id)){
            resId = userService.addUser(user);
        }else{
            user.setId(id);
            resId = userService.updateUser(user);
        }
        if(resId > 0){
            result.put("code",0);
            result.put("msg", I18nUtils.get("com.king.system.operation.ok"));
        }else{
            result.put("code",-1);
            result.put("msg", I18nUtils.get("com.king.system.operation.fail"));
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/del")
    public Object del(HttpServletRequest request,Long id){
        JSONObject result = new JSONObject();

        try{
            userService.delUser(id);
            result.put("code",0);
            result.put("msg", I18nUtils.get("com.king.system.operation.ok"));
        }catch (Exception ex){
            ex.printStackTrace();
            result.put("code",-1);
            result.put("msg", I18nUtils.get("com.king.system.operation.fail"));
        }
        return result;
    }

}
