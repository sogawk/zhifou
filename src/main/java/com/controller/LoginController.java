package com.controller;

import com.bean.HostHolder;

import com.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;


    @RequestMapping("/doRegister")
    public String doRegister(Model model,
                             HttpSession session,
                             @RequestParam("userName") String name,
                             @RequestParam("password") String password,
                             @RequestParam("rememberMe") boolean rememberMe,
                             HttpServletResponse response) {

        Map map = userService.register(name, password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");
//todo 如果没记住。。。
            if (rememberMe) {
                cookie.setMaxAge(3600 * 24 * 5);
            }
            response.addCookie(cookie);

        } else {
            model.addAttribute("message", map.get("message"));
            return "login";
        }

        return "redirect:/";
    }

    @RequestMapping({"/login"})
    public String index() {
        return "login";
    }

    @RequestMapping("/doLogin")
    public String doLogin(Model model,
                          HttpSession session,
                          HttpServletResponse response,
                          @RequestParam("userName") String name,
                          @RequestParam("password") String password,
                          @RequestParam("rememberMe") boolean rememberMe) {

        Map map = userService.doLogin(name, password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");

            if (rememberMe) {
                cookie.setMaxAge(3600 * 24 * 5);
            }
            response.addCookie(cookie);

            return "redirect:/";
        } else {
            model.addAttribute("message", map.get("message"));
            return "login";
        }

    }

    @RequestMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

    @RequestMapping("/")
    public String temp() {
        return "index";
    }
}
