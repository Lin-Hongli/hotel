package com.lhl.hotelweb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class IndexController {

    @Value("${github.client.id}")
    private String clientId;


    @Value("${github.redirect.uri}")
    private String redirectURI;

    private int state;


    @GetMapping("/")
    public String getIndex(Model model){
        state = new Random().nextInt(9999)+1;

        model.addAttribute("gitHubAuthorizeURL","https://github.com/login/oauth/authorize?client_id="+clientId+"&redirect_uri="+redirectURI+"&scope=user&state="+state);
        System.out.println(model.getAttribute("gitHubAuthorizeURL"));
        return "index";
    }
}
