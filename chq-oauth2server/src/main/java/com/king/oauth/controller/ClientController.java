package com.king.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.king.base.Page;
import com.king.sys.Oauth2Client;
import com.king.sys.service.IClientService;
import com.king.utils.AppUtils;
import com.king.utils.I18nUtils;
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

    @ResponseBody
    @RequestMapping("/save")
    public Object save(HttpServletRequest request,Long id, String clientName,String clientId,String clientSecret){
        Oauth2Client client = new Oauth2Client();
        client.setClientName(clientName);
        JSONObject result = new JSONObject();
        Long resId = 0l;
        if(StringUtils.isEmpty(id)){
            String appId = AppUtils.getAppId();
            client.setClientId(appId);
            client.setClientSecret(AppUtils.getAppSecret(appId));
            resId = clientService.addClient(client);
        }else{
            client.setId(id);
            //client.setClientId(clientId);
            //client.setClientSecret(clientSecret);
            resId = clientService.modifyClient(client);
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
            clientService.delClient(id);
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
