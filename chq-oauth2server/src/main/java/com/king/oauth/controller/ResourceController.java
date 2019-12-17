package com.king.oauth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.sys.SysResource;
import com.king.sys.service.ISysResourceService;
import com.king.utils.I18nUtils;
import com.king.utils.StringUtils;
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
@RequestMapping("sys/resource")
public class ResourceController {

    @Autowired
    private ISysResourceService sysResourceService;

    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request, Long menuId, Model model){
        model.addAttribute("menuId",menuId);
        return "sys/resourceMgr";
    }

    @RequestMapping("/all")
    @ResponseBody
    public Object findAll(Long roleId){
        List<SysResource> ownerRes = sysResourceService.getResourceByRoleId(roleId);
        List<SysResource> allRes = sysResourceService.findAll();

        Map<Long,Object> ownerMap = new HashMap<>();
        for(SysResource s : ownerRes){
            if(!ownerMap.containsKey(s.getId())){
                ownerMap.put(s.getId(),null);
            }
        }
        JSONArray roots = new JSONArray();
        for(SysResource s : allRes){
            if(s.getPid() == -1){
                JSONObject node = createNode(s,ownerMap);
                //递归子节点
                JSONArray children = hasNextNodes(allRes, s, ownerMap);
                node.put("children",children);
                roots.add(node);
            }
        }
        return roots;
    }

    private JSONArray hasNextNodes(List<SysResource> allRes,SysResource parent, Map<Long, Object> ownerMap) {
        JSONArray nodes = new JSONArray();
        for(SysResource s : allRes){
            if(s.getPid() == parent.getId()){
                JSONObject node = createNode(s,ownerMap);
                JSONArray children = hasNextNodes(allRes,s,ownerMap);
                node.put("children",children);
                nodes.add(node);
            }
        }
        return nodes;
    }

    private JSONObject createNode(SysResource res,Map<Long,Object> map){
        JSONObject node = new JSONObject();
        node.put("id",res.getId());
        node.put("pid",res.getPid());
        node.put("text",res.getName());
        node.put("state","open");
        node.put("type",res.getType());
        node.put("url",res.getUrl());
        node.put("permission",res.getPermission());
        if(map.containsKey(res.getId())){
            node.put("checked",true);
        }else{
            node.put("checked",false);
        }
        //node.put("iconCls",""); //可以自定义图标样式
        return node;
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(Long pid,Long id,String name,Integer type,String url,String permission){
        JSONObject result = new JSONObject();
        Long resId = 0l;

        SysResource res = new SysResource();
        if(StringUtils.isEmpty(pid)){
            res.setPid(-1L);
        }else{
            res.setPid(pid);
        }
        if(!StringUtils.isEmpty(name)){
            res.setName(name);
        }
        if(!StringUtils.isEmpty(type)){
            res.setType(type);
        }
        if(!StringUtils.isEmpty(url)){
            res.setUrl(url);
        }
        if(!StringUtils.isEmpty(permission)){
            res.setPermission(permission);
        }

        if(StringUtils.isEmpty(id)){
            resId = sysResourceService.addResource(res);
        }else{
            res.setId(id);
            resId = sysResourceService.modifyResource(res);
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
            sysResourceService.delResource(id);
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
