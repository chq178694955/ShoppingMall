package com.king.oauth.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@Configuration
public class ShiroConfig {

    //配置密码验证器
    @Bean
    public CredentialsMatcher credentialsMatcher(){
        return new MyMatcher();
    }

    //配置权限验证器
    @Bean
    public MyRealm myRealm(@Qualifier("credentialsMatcher")CredentialsMatcher credentialsMatcher){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(credentialsMatcher);
        myRealm.setCachingEnabled(false);
        return myRealm;
    }

    //回话ID生成器
    @Bean
    public JavaUuidSessionIdGenerator javaUuidSessionIdGenerator(){
        JavaUuidSessionIdGenerator javaUuidSessionIdGenerator = new JavaUuidSessionIdGenerator();
        return javaUuidSessionIdGenerator;
    }

    @Value("${spring.redis.host}")
    public String redisHost;
    @Value("${spring.redis.port}")
    public Integer redisPort;

    @Bean
    public RedisManager redisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost);
        redisManager.setPort(redisPort);
        redisManager.setExpire(1800);// 配置缓存过期时间
        redisManager.setTimeout(0);
        //redisManager.setPassword();//为空可以不配置
        return redisManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    //配置缓存验证器
    @Bean
    public CacheManager cacheManager(){
        //内存缓存管理器
        //return new MemoryConstrainedCacheManager();

        //redis缓存管理器
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    //配置记住我Cookie对象参数，rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    //配置Cookie管理对象，rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey("cao-king-cookies".getBytes());//cookie key 加密，长度必须16位
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    //注入自定义记住我过滤器
    @Bean
    public MyRememberFilter myRememberFilter(){
        return new MyRememberFilter();
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setCacheManager(cacheManager());
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionIdCookieEnabled(true);
//        sessionManager.setSessionIdCookie(rememberMeCookie());
        sessionManager.setSessionIdUrlRewritingEnabled(false);//隐藏jsessionid
        return sessionManager;
    }

    //配置securityManager安全管理器，主要起到一个桥梁作用
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("myRealm")MyRealm myRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //注入自定义myRealm
        defaultWebSecurityManager.setRealm(myRealm);
        //注入自定义cacheManager
        defaultWebSecurityManager.setCacheManager(cacheManager());
        //注入记住我管理器
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        //注入自定义sessionManager
        defaultWebSecurityManager.setSessionManager(sessionManager());
        return defaultWebSecurityManager;
    }

    //进行全局配置，Filter工厂。设置对应的过滤条件和跳转条件，有自定义的过滤器，有shiro认证成功后，失败后，退出后等跳转的页面，有静态页面等内容的权限范围
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
        //shiro对象
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);

        Map<String, Filter> filterMap=new LinkedHashMap<String,Filter>();
        filterMap.put("myRememberFilter",myRememberFilter());

        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        /*
        认证顺序是从上往下执行。
         */
        linkedHashMap.put("/logout", "logout");//在这儿配置登出地址，不需要专门去写控制器。

        linkedHashMap.put("/css/**", "anon");
        linkedHashMap.put("/fonts/**", "anon");
        linkedHashMap.put("/img/**", "anon");
        linkedHashMap.put("/js/**", "anon");

        linkedHashMap.put("/login", "authc");//拦截登录
        linkedHashMap.put("/oauth-server/authorize", "anon");
        linkedHashMap.put("/oauth-server/accessToken", "anon");
        linkedHashMap.put("/oauth-server/userInfo", "anon");

//        linkedHashMap.put("/oauth-server/**", "anon");//过滤掉授权验证请求地址

        linkedHashMap.put("/**", "user");//需要进行权限验证
        bean.setFilterChainDefinitionMap(linkedHashMap);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        bean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
//        bean.setSuccessUrl("/index");
        return bean;
    }

    //配置shiro的生命周期
    //使用springboot整合shiro时，@value注解无法读取application.yml中的配置
    //解决方法:将LifecycleBeanPostProcessor的配置方法改成静态的就可以了
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //配置shiro注解是否生效
    //启动Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
    //配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }

    //配置前台的shiro标签，使其能够使用
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
