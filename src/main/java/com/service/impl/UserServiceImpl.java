package com.service.impl;

import com.bean.LoginTicket;
import com.bean.User;
import com.mapper.TicketMapper;
import com.mapper.UserMapper;
import com.service.TicketService;
import com.service.UserService;
import com.util.zhifouUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TicketMapper ticketMapper;

    @Override
    public Map register(String name, String password) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            map.put("message", "用户名为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("message", "密码为空");
            return map;
        }

        User user = userMapper.selectUserByName(name);
        if (user != null) {
            map.put("message", "用户名已被注册");
            return map;
        }

        user = new User();
        user.setName(name);
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(zhifouUtil.MD5(password+ user.getSalt()));
        userMapper.insertUser(user);

        user = userMapper.selectUserByName(name);

//        登陆
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    @Override
    public User selectUserByName(String name) {
        return userMapper.selectUserByName(name);
    }

    @Override
    public Map doLogin(String name, String password) {
        Map map = new HashMap();
        if (StringUtils.isBlank(name)) {
            map.put("message", "用户名为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("message", "密码为空");
            return map;
        }

        User user = userMapper.selectUserByName(name);
        if (user == null) {
            map.put("message", "用户名不存在");
            return map;
        }

        if (!zhifouUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("message", "密码错误");
            return map;
        }
        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        map.put("userId", user.getId());
        return map;
    }

    @Override
    public void logout(String ticket) {
        ticketMapper.deleteByTicket(ticket);
    }

    public String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        Date date = new Date();
        date.setTime(date.getTime()+ 3600000 * 24 * 5);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        ticketMapper.insertTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
