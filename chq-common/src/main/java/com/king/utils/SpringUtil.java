package com.king.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @创建人 chq
 * @创建时间 2019/12/26
 * @描述
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    /**
     * 上下文实例
     */
    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 通过name获取bean对象
     */
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
}
