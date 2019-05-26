package com.wry.shiro.config;

import com.wry.shiro.mapper.SysUserMapper;
import com.wry.shiro.pojo.SysPermission;
import com.wry.shiro.pojo.SysRole;
import com.wry.shiro.pojo.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     * 执行授权的逻辑
     * @param principalCollection
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("------> 执行授权的逻辑");
        //获取登陆用户
        SysUser user= (SysUser) SecurityUtils.getSubject().getPrincipal();

        // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        List<SysPermission> permissionList = user.getPermissions();
        for(SysPermission permission: permissionList){
            info.addStringPermission(permission.getUrl());
        }

        List<SysRole> roleList = user.getRoles();
        for (SysRole role :roleList ) {
            info.addRole(role.getName());
        }
        return info;
    }

    /**
     * 执行认证的逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("------> 执行认证的逻辑");
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        SysUser loginUser = sysUserMapper.findByUsername(username);
        if (loginUser==null){
            throw  new UnknownAccountException();
        }
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(loginUser,loginUser.getPassword(),getName());
        return info;
    }
}
