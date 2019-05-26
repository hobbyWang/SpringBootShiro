package com.wry.shiro.config;

import com.wry.shiro.mapper.SysPermissionMapper;
import com.wry.shiro.mapper.SysRoleMapper;
import com.wry.shiro.pojo.SysPermission;
import com.wry.shiro.pojo.SysRole;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * ShiroFilterFactoryBean
     *
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(WebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //设置登录Url
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置登陆成功的url
        shiroFilterFactoryBean.setSuccessUrl("/");
        //设置没有权限的Url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        /**
         *  Shiro 内置过滤器
         *      anon:无需认证（登录）可访问
         *      authc:认证才可以
         *      user:如果使用remenberMe的功能才能访问
         *      perms:得到资源权限可以访问 perms[/admin]
         *      roles:得到角色权限可以访问 roles[ROLE_ADMIN]
         */
        Map<String, String> filterMap = new LinkedHashMap<>();
        //logout是shiro提供的过滤器
        filterMap.put("/logout", "logout");

        filterMap.put("/css/**", "anon");
        filterMap.put("/login", "anon");

        //根据数据库的资源设置权限  也可以根据角色设置权限
        List<SysPermission> permissionList = sysPermissionMapper.findByAll(null);

        for (SysPermission permission : permissionList) {
            if (!StringUtils.isEmpty(permission.getUrl())) {
                String permissionValue = "perms[" + permission.getUrl() + "]";
                //filterMap.put("/admin", "perms[/admin]");
                filterMap.put(permission.getUrl(), permissionValue);
            }
        }

//       url 不能重复
//        List<SysRole> roleList = sysRoleMapper.findByAll(null);
//        for (SysRole role : roleList) {
//            String permissionValue = "roles[" + role.getName() + "]";
//            for (SysPermission permission : role.getPermissions()) {
//                //filterMap.put("/admin", "roles[ROLE_ADMIN]");
//                filterMap.put(permission.getUrl(), permissionValue);
//            }
//        }

        //所有的其他url都必须认证才能访问
        filterMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }

    /**
     * SecurityManager
     *
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm) {
        return new DefaultWebSecurityManager(realm);
    }

    /**
     * Realm
     *
     * @return
     */
    @Bean
    public Realm realm() {
        return new CustomRealm();
    }
}
