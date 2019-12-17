package com.king.oauth.controller;

import com.king.sys.SysDict;
import com.king.sys.service.ISysDictService;
import com.king.utils.I18nUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @创建人 chq
 * @创建时间 2019/12/17
 * @描述
 */
@Controller
@RequestMapping("sys/dict")
public class DictController extends BaseController {

    @Autowired
    private ISysDictService sysDictService;

    @RequestMapping("/findByClass")
    @ResponseBody
    public Object findByClass(Integer classNo,Boolean isMultiple){
        List<SysDict> dicts = sysDictService.findDictByClass(classNo);
        if(null != dicts && dicts.size() > 0){
            SysDict dict = new SysDict();
            dict.setDictNo(-1);
            dict.setDictDesc(isMultiple ? I18nUtils.get("com.king.system.select.selected") : I18nUtils.get("com.king.system.select.all"));
            dicts.add(0,dict);
        }
        return dicts;
    }

}
