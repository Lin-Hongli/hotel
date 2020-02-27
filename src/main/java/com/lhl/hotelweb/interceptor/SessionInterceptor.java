package com.lhl.hotelweb.interceptor;

import com.lhl.hotelweb.dao.UserMapper;
import com.lhl.hotelweb.model.User;
import com.lhl.hotelweb.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("目标方法执行之前");
        //在访问所有请求之前，检查cookie，查询并返回user
        //拿到key为token的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //若拿到token
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    System.out.println("拿到cookie" + token);

                    //通过token验证是否登录过
                    UserExample userExample = new UserExample();
                    userExample.clear();
                    userExample.or().andTokenEqualTo(token);
                    //若登录过，通过token拿到数据库中的用户信息，返回给前端
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() > 0) {
                        User user = users.get(0);
                        System.out.println("访问首页拿到user" + user);
                        request.getSession().setAttribute("user", user);
                    }else {
                        request.getSession().setAttribute("user",null);
                    }
                    break;
                }

            }
        }

        //放行请求
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("目标方法执行之后");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("请求执行结束之后");
    }
}
