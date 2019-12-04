package com.king;

import com.king.starter.Startup;
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
    }

}
