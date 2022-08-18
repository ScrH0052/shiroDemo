package com.scrh.shirodemo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.config.ShiroConfiguration;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig extends ShiroConfiguration {

    //ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@PathVariable(name = "defaultWebSecurityManager")DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录请求的链接
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //设置登录成功跳转页面
        shiroFilterFactoryBean.setSuccessUrl("/");
        //访问当前用户无权访问的页面时时跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        //添加页面过滤
        /*
            添加Shiro内置过滤器，常用的有如下过滤器：
            anon： 无需认证就可以访问
            authc： 必须认证才可以访问
            user： 如果使用了记住我功能就可以直接访问
            perms: 拥有某个资源权限才可以访问
            role： 拥有某个角色权限才可以访问
        */
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/", "anon");
        filterMap.put("/toLogin", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/user/add", "perms[user:add]");
        filterMap.put("/user/update", "perms[user:update]");
        filterMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);

        return shiroFilterFactoryBean;
    }
    //DefaultWebSecurityManager
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@PathVariable(name = "userRealm")UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置自定义的shiroRealm
        securityManager.setRealm(userRealm);
        //设置打开rememberMe功能
        securityManager.setRememberMeManager(rememberMeManager());
        //设置自定义的session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }
    //创建 realm 对象，需要自定义类
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        //设置加密方式
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(10);
        userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRealm;
    }
    //配置Thymeleaf的shiro方言
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


    //rememberMe的配置
    //rememberMe的cookie
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setCookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：

        //setCookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //记住我cookie生效时间30天 ,单位秒;
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }
    //rememberMe的管理器
    @Bean
    public CookieRememberMeManager rememberMeManager(@PathVariable("rememberMeCookie")SimpleCookie rememberMeCookie){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie);
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    //rememberMe的过滤器
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter(){
        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
        //对应前端的checkbox的name = rememberMe
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        return formAuthenticationFilter;
    }

    //移除自带的jsessionid，防止第二次打开浏览器时进行注销操作发生jsessionid不同的异常
    @Override
    @Bean
    public DefaultSessionManager sessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }
}
