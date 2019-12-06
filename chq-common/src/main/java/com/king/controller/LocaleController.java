package com.king.controller;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * 读取国际化资源文件，加载资源文件数据
 * @创建人 chq
 * @创建时间 2019/12/6
 * @描述
 */
@RestController
public class LocaleController {

    private static final String SYMBOL = "_";

    /**
     * 通过前台传入国际化文件数组名来读取资源文件
     * @param request
     * @param filenames
     * @return
     * @throws IOException
     */
    @RequestMapping("/golbalLoadI18n")
    public Object golbalLoadsI18n(HttpServletRequest request,String filenames) throws IOException {
        Locale locale = Locale.getDefault();
        List<String[]> result = new ArrayList<>();
        Map<Object,Object> dataMap = new HashMap<>();
        String[] files = filenames.split(",");
        StringBuilder propFile = new StringBuilder();
        Properties properties = null;
        for(String file : files){
            propFile.setLength(0);
            propFile.append("i18n/")
                    .append(file)
                    .append(SYMBOL)
                    .append(locale.getLanguage())
                    .append(SYMBOL)
                    .append(locale.getCountry())
                    .append(".properties");
            properties = PropertiesLoaderUtils.loadAllProperties(propFile.toString());
            dataMap = properties;
            for(Map.Entry<Object,Object> item : dataMap.entrySet()){
                result.add(new String[]{item.getKey().toString(),item.getValue().toString()});
            }
        }
        return result;
    }

}
