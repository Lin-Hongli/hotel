package com.lhl.hotelweb.controller;

import com.lhl.hotelweb.dao.UserMapper;
import com.lhl.hotelweb.exception.CustomizeErrorCode;
import com.lhl.hotelweb.exception.CustomizeException;
import com.lhl.hotelweb.exception.ICustomizeErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;


    @Value("${github.redirect.uri}")
    private String redirectURI;

    private int state;

    @GetMapping("/")
    public String getIndex(HttpServletRequest request,
                           HttpServletResponse response,
                           Model model){

        //访问首页前先通过拦截器检查登录状态，若没登录，则session中的user==null
        state = new Random().nextInt(9999) + 1;

        if (request.getSession().getAttribute("user") == null) {
            System.out.println("user==null");
            //若拿不到cookie=token,前端GitHub授权登录按钮
            model.addAttribute("clientId",clientId);
            model.addAttribute("redirectURI",redirectURI);
            model.addAttribute("state",state);
        }

        return "index";
    }

    //退出登录
    @GetMapping("/logout")
    public String logout(HttpServletResponse response,
                         HttpServletRequest request){
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        request.getSession().removeAttribute("user");
        return "redirect:/";
    }

}
