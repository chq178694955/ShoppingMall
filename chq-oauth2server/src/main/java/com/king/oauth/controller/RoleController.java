package com.king.oauth.controller;

import com.king.base.Page;
import com.king.sys.SysRole;
import com.king.sys.SysUser;
import com.king.sys.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
