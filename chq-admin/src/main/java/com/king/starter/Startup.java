package com.king.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * WEB入口
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.king"})
public class Startup {

    public static void main(String[] args) {
        SpringApplication.run(Startup.class,args);
    }

}
