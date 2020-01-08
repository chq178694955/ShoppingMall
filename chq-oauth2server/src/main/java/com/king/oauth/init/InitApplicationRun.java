package com.king.oauth.init;

import com.king.sys.service.ISysTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @创建人 chq
 * @创建时间 2020/1/6
 * @描述
 */
@Component
public class InitApplicationRun  implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(InitApplicationRun.class);

    @Autowired
    ISysTaskService sysTaskService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("统一认证授权应用启动...");

        sysTaskService.initTaks();
        logger.info("初始化任务完成...");
    }

}
