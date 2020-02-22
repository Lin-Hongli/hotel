package com.lhl.hotelweb.impl;

import com.lhl.hotelweb.dao.UserMapper;
import com.lhl.hotelweb.entity.User;
import com.lhl.hotelweb.entity.UserExample;
import com.lhl.hotelweb.service.UserService;
import com.lhl.hotelweb.util.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    @Override
    public Msg userLogin(String account, String password) {
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria =userExample.createCriteria();
        criteria.andAccountEqualTo(account);
        long l = userMapper.countByExample(userExample);
        System.out.println("count:"+l);
        if (l==1){
            List<User> users = userMapper.selectByExample(userExample);
            User user= users.get(0);
            if(user.getPassword().equals(password)){
                return Msg.success().add("msg","登录成功");
            }else{
                System.out.println("密码不正确");
                return Msg.fail().add("msg","密码不正确");
            }
        }else if (l>1) {
            System.out.println("用户多于1个");
            return Msg.fail().add("msg","数据库账户重复");
        }else{
            System.out.println("用户不存在");
            return Msg.fail().add("msg","用户不存在");
        }
    }

    @Override
    public Msg addUser(User user) {
        Map<String,String> msgMap=new HashMap<String,String>();
        boolean flag=true;
        UserExample userExample=new UserExample();

        userExample.clear();
        /*生成一个新的Criteria对象*/
        userExample.or().andUsernameEqualTo(user.getUsername());
        long countUsername = userMapper.countByExample(userExample);

        userExample.clear();
        userExample.or().andAccountEqualTo(user.getAccount());
        long countAccount = userMapper.countByExample(userExample);

        userExample.clear();
        userExample.or().andEmailEqualTo(user.getEmail());
        long countEmail = userMapper.countByExample(userExample);

        userExample.clear();
        userExample.or().andTelEqualTo(user.getTel());
        long countTel = userMapper.countByExample(userExample);

        if(countUsername>0){
            msgMap.put("username_check_msg","用户名已被注册");
            flag=false;
        }else {
            msgMap.put("username_check_msg","用户名可用");
        }

        if(countAccount>0){
            msgMap.put("account_check_msg","账号已被注册");
            flag=false;
        }else {
            msgMap.put("account_check_msg","账号可用");
        }


        System.out.println(countEmail);
        if(countEmail>0){
            msgMap.put("email_check_msg","邮箱已被注册");
            flag=false;
        }else {
            msgMap.put("email_check_msg","邮箱可用");
        }

        if(countTel>0){
            msgMap.put("tel_check_msg","电话已被注册");
            flag=false;
        }else {
            msgMap.put("tel_check_msg","电话可用");
        }

        if (flag){
            userMapper.insert(user);
            return Msg.success().add("addUserSuccess","注册成功");
        }

        return Msg.fail().add("addUserMsg",msgMap);
    }
}
