package com.king;

import com.king.starter.Startup;
import org.apache.shiro.crypto.hash.Sha384Hash;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Startup.class)
public class Test {

    @org.junit.Test
    public void test1(){
        System.out.println("test1");
        String sha384Hex=new Sha384Hash("123456").toBase64();
        System.out.println(sha384Hex);
    }

}
