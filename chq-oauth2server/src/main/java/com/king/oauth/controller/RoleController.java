package com.king.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.king.base.Page;
import com.king.sys.SysRole;
import com.king.sys.service.ISysRoleService;
import com.king.utils.I18nUtils;
import com.king.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @创建人 chq
 * @创建时间 2019/12/8
 * @描述
 */
@Controller
@RequestMapping("sys/role")
public class RoleController extends BaseController{

    @Autowired
    private ISysRoleService sysRoleService;

    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request, Long menuId, Model model){
        model.addAttribute("menuId",menuId);
        return "sys/roleMgr";
    }

    @ResponseBody
    @RequestMapping("/queryAll")
    public List<SysRole> queryAll(HttpServletRequest request){
        return sysRoleService.getAll();
    }

    @ResponseBody
    @RequestMapping("/query")
    public Object query(HttpServletRequest request){
        Page<SysRole> page = this.getPage(request);
        Map<String,String> params = new HashMap<>();
        page = sysRoleService.find(page,params);
        return this.getGridData(page);
    }

    @ResponseBody
    @RequestMapping("/save")
    public Object save(HttpServletRequest request,Long id, String name,String checkNodes){
        SysRole role = new SysRole();
        if(!StringUtils.isEmpty(name)){
            role.setName(name);
        }

        Set<Long> ids = new HashSet<>();
        if(!StringUtils.isEmpty(checkNodes)){
            String[] strs = checkNodes.split(",");
            for(String s : strs){
                ids.add(Long.parseLong(s));
            }
        }

        role.setResourceIds(ids);

        JSONObject result = new JSONObject();
        Long resId = 0l;
        if(StringUtils.isEmpty(id)){
            resId = sysRoleService.addRole(role);
        }else{
            role.setId(id);
            resId = sysRoleService.modifyRole(role);
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
            sysRoleService.delRole(id);
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
