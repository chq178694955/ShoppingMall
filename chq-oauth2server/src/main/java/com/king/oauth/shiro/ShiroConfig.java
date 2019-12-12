package com.king.oauth.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
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

    // 注意/r/n前不能有空格
    private static final String CRLF = "\r\n";

    private static final String MD5 = "md5";

    @Value("${spring.redis.host}")
    public String redisHost;
    @Value("${spring.redis.port}")
    public Integer redisPort;

    @Bean(name="hashedCredMatcher")
    public HashedCredentialsMatcher createHashCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher hcm = new RetryLimitHashedCredentialsMatcher();
        hcm.setHashAlgorithmName(MD5);
        hcm.setStoredCredentialsHexEncoded(true);
        return hcm;
    }

    @Bean(name="userAuthc")
    public MyRealm getUserAuthcRealm() {
        MyRealm uar = new MyRealm();
        uar.setCredentialsMatcher(createHashCredentialsMatcher());
        return uar;
    }

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

    //配置记住我Cookie对象参数，rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等
    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    //配置Cookie管理对象，rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey("ser-king-cookies".getBytes());//cookie key 加密，长度必须16位
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    //注入自定义记住我过滤器
//    @Bean
//    public MyRememberFilter myRememberFilter(){
//        return new MyRememberFilter();
//    }

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

    /*
    @Bean(name="cacheManager")
    public EhCacheManager createEhcacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        String path = "classpath:ehcache.xml";
        cacheManager.setCacheManagerConfigFile(path);
        return cacheManager;
    }
    */

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

    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入自定义myRealm
        securityManager.setRealm(getUserAuthcRealm());
        //注入自定义cacheManager
        securityManager.setCacheManager(cacheManager());
        //注入记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());
        //注入自定义sessionManager
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 自定义的shiro相关的Filter在这里做统一处理
     * */
    public Map<String, Filter> createFilterChainMap() {
        Map<String, Filter> filters = new LinkedHashMap<>();
//        filters.put("rememberMeFilter",myRememberFilter());
        return filters;
    }

    /**
     * 读取ini文获得固定权限
     * */
    public String loadFilterChainDefinitions() {
        StringBuffer sb = new StringBuffer();
        //	sb.append(getFixedAuthRule());//固定权限，采用读取配置文件

        sb.append("/css/** = anon").append(CRLF);
        sb.append("/fonts/** = anon").append(CRLF);
        sb.append("/images/** = anon").append(CRLF);
        sb.append("/js/** = anon").append(CRLF);
        sb.append("/components/** = anon").append(CRLF);

        sb.append("/loginAdmin = anon").append(CRLF);
        sb.append("/logout = logout").append(CRLF);
        sb.append("/login = anon").append(CRLF);
        sb.append("/oauth-server/authorize = anon").append(CRLF);
        sb.append("/oauth-server/accessToken = anon").append(CRLF);
        sb.append("/oauth-server/userInfo = anon").append(CRLF);
        sb.append("/** = user").append(CRLF);

        return sb.toString();
    }

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean createShiroSecurityFilterFactory() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl("/toLoginAdmin");
        //shiroFilter.setFilters(createFilterChainMap());
        //注意过滤器配置顺序 不能颠倒
  /*     filterChainDefinitionMap.put("/static/**", "anon");
         filterChainDefinitionMap.put("/templates/**", "anon");
         filterChainDefinitionMap.put("/g/unLogin", "anon");
         filterChainDefinitionMap.put("/g/login", "loginFtr");
         filterChainDefinitionMap.put("/g/logout", "authc");
         filterChainDefinitionMap.put("/g/unauthorized", "anon");
         filterChainDefinitionMap.put("/**", "permsFilter");*/
        shiroFilter.setFilterChainDefinitions(loadFilterChainDefinitions());
        shiroFilter.setFilters(createFilterChainMap());
        return shiroFilter;
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
