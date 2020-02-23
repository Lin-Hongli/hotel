package com.lhl.hotelweb.controller;

import com.lhl.hotelweb.dto.GitHubAccessTokenDTO;
import com.lhl.hotelweb.dto.GitHubUser;
import com.lhl.hotelweb.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    //GitHub授权登录
    @GetMapping("/callback")
    /*接收GitHub登录后回调的code和state*/
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){

        GitHubAccessTokenDTO gitHubAccessTokenDTO = new GitHubAccessTokenDTO();

        gitHubAccessTokenDTO.setClient_id(clientId);//客户id
        gitHubAccessTokenDTO.setClient_secret(clientSecret);//客户秘钥
        gitHubAccessTokenDTO.setRedirect_uri(redirectUri);//回调地址
        gitHubAccessTokenDTO.setCode(code);//前端用户授权登录之后返回的code
        gitHubAccessTokenDTO.setState(state);
        //GitHubProvider调用GitHub的接口，同时post传递需要的json数据，获取到AccessToken
        String accessToken = gitHubProvider.getAccessToken(gitHubAccessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
        System.out.println(gitHubUser.getName());
        return "index";
    }
}
