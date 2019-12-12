package com.king.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
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

    // 注意: /r/n前不能有空格
    private static final String CRLF = "\r\n";

    @Value("${clientId}")
    private String clientId;
    @Value("${clientSecret}")
    private String clientSecret;
    @Value("${accessTokenUrl}")
    private String accessTokenUrl;
    @Value("${userInfoUrl}")
    private String userInfoUrl;
    @Value("${redirectUrl}")
    private String redirectUrl;
    @Value("${authorizeUrl}")
    private String authorizeUrl;

    //配置权限验证器
    @Bean
    public Oauth2Realm getUserAuthcRealm(){
        Oauth2Realm myRealm = new Oauth2Realm();
        myRealm.setCachingEnabled(true);
        myRealm.setAuthenticationCachingEnabled(true);
        myRealm.setAuthenticationCacheName("authenticationCache");
        myRealm.setAuthorizationCachingEnabled(true);
        myRealm.setAuthenticationCacheName("authorizationCache");
        return myRealm;
    }

    public Map<String, Filter> createFilterChainMap() {
        Map<String, Filter> filters = new LinkedHashMap<>();
        OAuth2AuthenticationFilter oAuth2AuthenticationFilter =
                new OAuth2AuthenticationFilter();
        oAuth2AuthenticationFilter.setAuthcCodeParam(OAuth.OAUTH_CODE);
        oAuth2AuthenticationFilter.setResponseType(OAuth.OAUTH_CODE);
        oAuth2AuthenticationFilter.setFailureUrl("/oauth2Failure");
        oAuth2AuthenticationFilter.setClientId(clientId);
        oAuth2AuthenticationFilter.setRedirectUrl(redirectUrl);
        filters.put("oauth2Authc",oAuth2AuthenticationFilter);
        return filters;
    }

    @Bean(name="cacheManager")
    public EhCacheManager createEhcacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        String path = "classpath:ehcache.xml";
        cacheManager.setCacheManagerConfigFile(path);
        return cacheManager;
    }

    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityMananger = new DefaultWebSecurityManager();
        securityMananger.setRealm(getUserAuthcRealm());
        securityMananger.setCacheManager(createEhcacheManager());
        // securityMananger.setRememberMeManager(createCookieRemmberMananger());
        return securityMananger;
    }

    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean createShiroSecurityFilterFactory() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());
        shiroFilter.setLoginUrl(authorizeUrl+"?client_id="+clientId+"&response_type=" + OAuth.OAUTH_CODE +
                "&redirect_uri=" + redirectUrl);
        shiroFilter.setSuccessUrl("/");
        shiroFilter.setFilters(createFilterChainMap());
        shiroFilter.setFilterChainDefinitions(loadFilterChainDefinitions());
        return shiroFilter;
    }

    public String loadFilterChainDefinitions() {
        StringBuffer sb = new StringBuffer();
        //	sb.append(getFixedAuthRule());//固定权限，采用读取配置文件
        sb.append("/oauth2Failure = anon").append(CRLF);
        sb.append("/oauth-client/callbackCode = oauth2Authc").append(CRLF);
//        sb.append("/oauth-client/callbackCode = anon").append(CRLF);
//        sb.append("/oauth-client/getCode = anon").append(CRLF);
//        sb.append("/oauth-client/getUserInfo = anon").append(CRLF);
        sb.append("/logout = logout").append(CRLF);

//        sb.append("/ = oauth2Authc").append(CRLF);
        sb.append("/** = user").append(CRLF);

        return sb.toString();
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
