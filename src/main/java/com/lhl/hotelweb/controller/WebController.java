package com.lhl.hotelweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
public class WebController {

    @GetMapping("/hello")
    public String webController(){
        return "hello world";
    }



}
