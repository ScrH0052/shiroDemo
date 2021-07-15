package com.scrh.shirodemo.controller;

import com.scrh.shirodemo.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    //登录操作
    @RequestMapping("/login")
    public String login(String username, String password, boolean rememberMe, Model model, HttpSession session) {
        //使用shiro，编写认证操作
        //1. 获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2. 封装用户的数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        //3. 执行登录的方法，只要没有异常就代表登录成功！
        try {
            subject.login(token); //登录成功！返回首页
            Subject currentSubject = SecurityUtils.getSubject();
            User user = (User) currentSubject.getPrincipal();
//            session.setAttribute("user",user);
//            //将用户信息保存30天
//            session.setMaxInactiveInterval(2592000);
            return "redirect:/";
        } catch (UnknownAccountException e) { //用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "/login";
        } catch (IncorrectCredentialsException e) { //密码错误
            model.addAttribute("msg", "密码错误");
            return "/login";
        }


    }

    @RequestMapping("/logout")
    public String logout() {
        //1. 获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2. 登出注销
        subject.logout();
        return "redirect:/";
    }

    @RequestMapping("/noAuth")
    @ResponseBody
    public String noAuth() {
        return "<h2 style='color:red'>您的权限不足，访问被拒绝</h2>";
    }
}
