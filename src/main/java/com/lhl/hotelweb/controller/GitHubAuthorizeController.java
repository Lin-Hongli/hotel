package com.lhl.hotelweb.controller;

import com.lhl.hotelweb.dao.UserMapper;
import com.lhl.hotelweb.dto.GitHubAccessTokenDTO;
import com.lhl.hotelweb.dto.GitHubUser;
import com.lhl.hotelweb.exception.CustomizeException;
import com.lhl.hotelweb.model.User;
import com.lhl.hotelweb.model.UserExample;
import com.lhl.hotelweb.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
public class GitHubAuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    //GitHub授权登录
    @GetMapping("/callback")
    /*接收GitHub登录后回调的code和state*/
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        GitHubAccessTokenDTO gitHubAccessTokenDTO = new GitHubAccessTokenDTO();

        gitHubAccessTokenDTO.setClient_id(clientId);//客户id
        gitHubAccessTokenDTO.setClient_secret(clientSecret);//客户秘钥
        gitHubAccessTokenDTO.setRedirect_uri(redirectUri);//回调地址
        gitHubAccessTokenDTO.setCode(code);//前端用户授权登录之后返回的code
        gitHubAccessTokenDTO.setState(state);
        //GitHubProvider调用GitHub的接口，同时post传递需要的json数据，获取到AccessToken
        String accessToken = gitHubProvider.getAccessToken(gitHubAccessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        if (gitHubUser.getName()!= null) {
            //GitHub登录成功，写cookie，不用写session
            //而是在访问首页时，通过验证前端和数据库的token,判断是否登录过，
            //若token一致则登录过，前端不用显示登录按钮进行GitHub登录
            //若不一致，则显示登录按钮进行登录

            String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60*60*24);
            //cookie.setPath("/");//所有页面有效

            //转发到添加/user请求？？

            //验证是否已有此GitHub用户，若没有则添加GitHub用户，有则刷新token即可？？？
            UserExample userExample=new UserExample();
            userExample.clear();
            userExample.or().andAccountIdEqualTo(String.valueOf(gitHubUser.getId()));
            //通过accountID判断是否已有用户？？？
            if (userMapper.countByExample(userExample)>0){
                //有用户,更新前端token
                response.addCookie(cookie);
                //更新数据库token,username,头像等用户信息
                //步骤：通过andAccountId查出user，修改字段，提交更新
                List<User> users = userMapper.selectByExample(userExample);
                for (User userInfo : users) {
                    System.out.println("原有用户信息"+userInfo);
                    userInfo.setName(gitHubUser.getName());
                    userInfo.setToken(token);
                    //搞一个最近登录时间？？
                   /* userInfo.setGmtLogin(System.currentTimeMillis());*/
                    System.out.println("更新用户信息token,username,头像等信息"+userInfo);
                    userMapper.updateByExample(userInfo, userExample);
                }
            }else{
                //添加GitHub用户
                User user=new User();
                user.setToken(token);
                user.setName(gitHubUser.getName());
                user.setAccountId(String.valueOf(gitHubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userMapper.insert(user);
                System.out.println("没有的用户插入用户"+user);
                //session写入刚插入的用户，写入cookie
                request.getSession().setAttribute("user",user);
                response.addCookie(cookie);
            }
            return "redirect:/";
        }else {
            //用户信息有问题，好像多余，已经在GitHubProvider验证过
            System.out.println("登录失败！GitHub是国外服务器，网络可能较差，再登录试试？");
            throw new CustomizeException("登录失败！GitHub是国外服务器，网络可能较差，再登录试试？");
        }
    }
}
