package com.lhl.hotelweb.controller;



import com.lhl.hotelweb.model.User;
import com.lhl.hotelweb.service.impl.UserServiceImpl;
import com.lhl.hotelweb.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @ResponseBody
    @GetMapping("/login")
    public Msg userLogin(@RequestParam("account") String account,
                         @RequestParam("password") String password){
        System.out.println(account);
        System.out.println(password);
        /*调用登录方法，返回登录信息*/
        Msg msg = userService.userLogin(account, password);
        return msg;
    }



    /*用户注册*/
    @ResponseBody
    @PostMapping("/user")
    public Msg userRegister(User user){
        System.out.println(user);
        //校验用户信息合法性



        //调用添加方法，插入用户前校验数据库信息
        Msg msg = userService.addUser(user);
        return msg;
    }

}
