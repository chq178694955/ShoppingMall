package com.king.oauth.controller;

import com.github.pagehelper.PageHelper;
import com.king.base.Page;
import com.king.sys.Oauth2Client;
import com.king.sys.service.IClientService;
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
 * @创建时间 2019/12/9
 * @描述
 */
@Controller
@RequestMapping("sys/client")
public class ClientController extends BaseController{

    @Autowired
    private IClientService clientService;

    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request, Long menuId, Model model){
        model.addAttribute("menuId",menuId);
        return "sys/clientMgr";
    }

    @ResponseBody
    @RequestMapping("/query")
    public Object query(HttpServletRequest request){
        Page<Oauth2Client> page = this.getPage(request);
        Map<String,String> params = new HashMap<>();
        page = clientService.find(page,params);
        return this.getGridData(page);
    }

}
