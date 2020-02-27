package com.lhl.hotelweb.service;


import com.lhl.hotelweb.model.User;
import com.lhl.hotelweb.util.Msg;

public interface UserService {
    Msg userLogin(String account, String password);

    Msg addUser(User user);
}
