package com.lhl.hotelweb.service;

import com.lhl.hotelweb.entity.User;
import com.lhl.hotelweb.util.Msg;

public interface UserService {
    Msg userLogin(String account, String password);

    Msg addUser(User user);
}
