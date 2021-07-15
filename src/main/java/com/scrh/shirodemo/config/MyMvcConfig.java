package com.scrh.shirodemo.config;

import com.scrh.shirodemo.interceptor.AccountCheckInterceptor;
import com.scrh.shirodemo.interceptor.LoginAlreadyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/index").setViewName("/index");
        registry.addViewController("/user/add").setViewName("/user/add");
        registry.addViewController("/user/update").setViewName("/user/update");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //如果已经登录并经过认证，对于/toLogin 请求直接跳转至首页
        registry.addInterceptor(getLoginAlreadyInterceptor()).addPathPatterns("/toLogin");
        //添加对除登录页面外所有请求进行账户验证的拦截器
        registry.addInterceptor(getAccountCheckInterceptor()).addPathPatterns("/**").excludePathPatterns("/toLogin", "/login", "/logout", "/noAuth");
    }

    //将拦截器的注册添加至spring上下文中
    @Bean
    public HandlerInterceptor getAccountCheckInterceptor(){
        return new AccountCheckInterceptor();
    }
    @Bean
    public HandlerInterceptor getLoginAlreadyInterceptor(){
        return new LoginAlreadyInterceptor();
    }
}
