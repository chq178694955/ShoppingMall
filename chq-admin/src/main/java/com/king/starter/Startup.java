package com.king.starter;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Properties;

/**
 * WEB入口
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.king"})
@MapperScan("com.king.dao")
public class Startup {

    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties props = new Properties();
        props.setProperty("offsetAsPageNum","true");
        props.setProperty("rowBoundsWithCount","true");
        props.setProperty("reasonable","true");
        props.setProperty("dialect","mysql");
        pageHelper.setProperties(props);
        return pageHelper;
    }

    public static void main(String[] args) {
        SpringApplication.run(Startup.class,args);
    }

}
