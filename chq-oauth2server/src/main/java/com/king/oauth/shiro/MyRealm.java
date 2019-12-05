package com.king.oauth.shiro;

import com.king.sys.SysResource;
import com.king.sys.SysRole;
import com.king.sys.SysUser;
import com.king.sys.service.ISysResourceService;
import com.king.sys.service.ISysRoleService;
import com.king.sys.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 登录及权限验证
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
public class MyRealm extends AuthorizingRealm {

    @Autowired
    protected IUserService userService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysResourceService sysResourceService;

    //角色权限和对应权限添加
    //Authorization授权，将数据库中的角色和权限授权给输入的用户名
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录的用户名
        String account = (String)principalCollection.getPrimaryPrincipal();
        //到数据库里查询要授权的内容
        SysUser user = userService.getUserByAccount(account);
        if(user != null){
            //记录用户的所有角色和权限
            SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();//权限信息

            List<SysRole> roles = sysRoleService.getRolesByUserId(user.getId());
            for(SysRole role : roles){
                //将所有的角色信息添加进来
                simpleAuthorizationInfo.addRole(role.getName());
                List<SysResource> resources = sysResourceService.getResourceByRoleId(role.getId());
                for(SysResource res : resources){
                    //将此次遍历到的角色的所有权限拿到，添加·进来
                    simpleAuthorizationInfo.addStringPermission(res.getPermission());
                }
            }
            return simpleAuthorizationInfo;
        }

        return null;
    }

    //用户身份验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从token获取用户名,从主体传过来的认证信息中获取
        //加这一步的目的是在post请求时会先进入认证然后再到请求。
        if(authenticationToken.getPrincipal()==null){
            return null;
        }
        //获取用户的登录信息，用户名
        String account = authenticationToken.getPrincipal().toString();

        //根据service调用用户名，查找用户的全部信息
        //通过用户名到数据库获取凭证
        SysUser user = userService.getUserByAccount(account);
        if(user == null){
            //这里返回会报出对应异常
        }else{
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(account,user.getPassword(),getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }

}
