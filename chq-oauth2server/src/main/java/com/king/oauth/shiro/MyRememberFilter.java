package com.king.oauth.shiro;

import com.king.oauth.constant.Constants;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public class MyRememberFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject=getSubject(request,response);
        if(!subject.isAuthenticated() && subject.isRemembered()){
            if(subject.getSession().getAttribute(Constants.SESSION_KEY)==null &&subject.getPrincipal()!=null){
                subject.getSession().setAttribute(Constants.SESSION_KEY,subject.getPrincipal());
            }
        }
        return subject.isAuthenticated() || subject.isRemembered();
    }
}
