package com.wry.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {


    @RequestMapping("/login")
    public String login(String username, String password, Model model){
        //1.获取主体
        Subject subject= SecurityUtils.getSubject();
        //2.创建Token对象
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        //调用登陆的方法
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            model.addAttribute("msg","用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg","密码错误！");
            return "login";
        }
        return "redirect:index";

    }
}
