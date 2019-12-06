package com.king;

import com.king.starter.Startup;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.LocaleResolver;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class Test {

    @Autowired
    private LocaleResolver localeResolver;

    @org.junit.Test
    public void test1(){

//        System.out.println("test1");
//        String sha384Hex=new Sha384Hash("123456").toBase64();
//        System.out.println(sha384Hex);
        Locale locale = Locale.getDefault();
        System.out.println(locale.getCountry() + "|" + locale.getLanguage());
        Properties properties = new Properties();
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("i18n/messages_zh_CN.properties");
            System.out.println(new String(properties.getProperty("com.king.system.name")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
