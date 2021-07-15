package com.scrh.shirodemo.interceptor;

import com.scrh.shirodemo.pojo.User;
import com.scrh.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountCheckInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Subject currentSubject = SecurityUtils.getSubject();
        User user = (User) currentSubject.getPrincipal();

        if (user != null) {
            try {
                checkUserInfo(user,currentSubject);
            } catch (IncorrectCredentialsException e){
                //密码错误，登出帐号，跳转至登录界面，携带错误编号"02"
                currentSubject.logout();
                response.sendRedirect("/login");
                return false;
            }
        }

        return true;
    }



    //检验用户信息
    private void checkUserInfo(User user, Subject currentSubject){

        if(userService.checkUserInfo(user.getUsername(),user.getPassword())){
            currentSubject.getSession().setAttribute("user",user);
        }else {

            throw new IncorrectCredentialsException();
        }

    }
}
