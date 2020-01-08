package com.king.conf;

import com.king.factory.MyJobFactory;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @创建人 chq
 * @创建时间 2019/12/25
 * @描述
 */
@Configuration
public class SchedulerConfig {

    @Autowired
    private MyJobFactory myJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException{
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        //延时启动
        factory.setStartupDelay(15);
        //加载quartz数据源配置
        factory.setQuartzProperties(quartzProperties());
        //自定义jobFactory
        factory.setJobFactory(myJobFactory);

        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException{
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public QuartzInitializerListener executorListener(){
        return new QuartzInitializerListener();
    }

    @Bean(name="kingScheduler")
    public Scheduler scheduler()throws Exception{
        return schedulerFactoryBean().getScheduler();
    }

}
