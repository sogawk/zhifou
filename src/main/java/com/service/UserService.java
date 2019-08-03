package com.service;

import com.bean.User;
import com.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface UserService {
    Map register(String name, String password);

    User selectUserByName(String name);

    Map doLogin(String name, String password);

    void logout(String ticket);
}
