package com.scrh.shirodemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {


    @RequestMapping("/toLogin")
    public String toLogin(String err, Model model) {
        if (err == null ) {
            return "/login";
        } else if (err.equals("00")) {
            model.addAttribute("msg", "游客无法访问该页面，请登录");
        } else if (err.equals("01")) {
            model.addAttribute("msg", "权限等级不足，无法访问，请登录拥有足够权限的帐号进行访问");
        }
//        } else if (err.equals("02")){
//            model.addAttribute("msg", "帐号与密码不匹配");
//        }
        return "/login";
    }


}
