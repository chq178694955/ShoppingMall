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

    @Bean(name="rememberCookies")
    public SimpleCookie createSimpleCookies() {
        SimpleCookie sc = new SimpleCookie("rememberMe");
        sc.setHttpOnly(true);
        sc.setMaxAge(5);
        return sc;
    }

    @Bean(name="cookieRemmberMananger")
    public CookieRememberMeManager createCookieRemmberMananger() {
        CookieRememberMeManager crm = new CookieRememberMeManager();
        crm.setCookie(createSimpleCookies());
        return crm;
    }

    @Bean(name="cacheManager")
    public EhCacheManager createEhcacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        String path = "classpath:ehcache.xml";
        cacheManager.setCacheManagerConfigFile(path);
        return cacheManager;
    }

    @Bean(name="securityMananger")
    public DefaultWebSecurityManager createSecurityManager() {
        DefaultWebSecurityManager securityMananger = new DefaultWebSecurityManager();
        securityMananger.setRealm(getUserAuthcRealm());
        securityMananger.setCacheManager(createEhcacheManager());
        securityMananger.setRememberMeManager(createCookieRemmberMananger());
        return securityMananger;
    }

    /**
     * 自定义的shiro相关的Filter在这里做统一处理
     * */
    public Map<String, Filter> createFilterChainMap() {
        Map<String, Filter> filters = new LinkedHashMap<>();
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
        shiroFilter.setSecurityManager(createSecurityManager());
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
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityMananger") DefaultWebSecurityManager securityManager) {
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
