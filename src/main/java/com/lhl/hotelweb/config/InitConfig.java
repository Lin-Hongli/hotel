package com.lhl.hotelweb.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InitConfig {

    @PostConstruct
    public void inits(){
        System.out.println("初始化方法");
    }
}
