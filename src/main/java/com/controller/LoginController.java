package com.controller;

import com.bean.HostHolder;

import com.service.UserService;

import org.apache.commons.lang.StringUtils;
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


    @RequestMapping("/reg")
    public String doRegister(Model model,
                             HttpSession session,
                             @RequestParam("username") String name,
                             @RequestParam("password") String password,
                             @RequestParam(value = "rememberMe", defaultValue = "false")  boolean rememberMe,
                             @RequestParam(value = "next", required = false) String next,
                             HttpServletResponse response) {

        Map map = userService.register(name, password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");

            if (rememberMe) {
                cookie.setMaxAge(3600 * 24 * 5);
            }
            response.addCookie(cookie);

        } else {
            model.addAttribute("msg", map.get("message"));
            return "login";
        }

        if (StringUtils.isNotBlank(next)) {
            return "redirect:" + next;
        }
        return "redirect:/";
    }

    @RequestMapping({"/reglogin"})
    public String index(Model model,
                        @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        return "login";
    }

    @RequestMapping("/login")
    public String doLogin(Model model,
                          HttpSession session,
                          HttpServletResponse response,
                          @RequestParam("username") String name,
                          @RequestParam("password") String password,
                          @RequestParam(value = "rememberMe", defaultValue = "false")  boolean rememberMe,
                          @RequestParam(value = "next", required = false) String next) {

        Map map = userService.doLogin(name, password);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");

            if (rememberMe) {
                cookie.setMaxAge(3600 * 24 * 5);
            }
            response.addCookie(cookie);

            if (StringUtils.isNotBlank(next)) {
                return "redirect:" + next;
            }
            return "redirect:/";
        } else {
            model.addAttribute("msg", map.get("message"));
            return "login";
        }

    }

    @RequestMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }


}
