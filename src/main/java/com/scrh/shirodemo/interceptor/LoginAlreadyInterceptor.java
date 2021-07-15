package com.scrh.shirodemo.interceptor;

import com.scrh.shirodemo.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginAlreadyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            response.sendRedirect("/");
            return false;
        }else {
            return true;
        }
    }
}
