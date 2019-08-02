package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aa")
public class aa {

    @RequestMapping("/bb")
    public String method() {
        return "login";
    }
}
