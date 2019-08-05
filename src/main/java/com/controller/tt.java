package com.controller;

import com.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class tt {
    @Autowired
    SensitiveService sensitiveService;

    @RequestMapping("tt")
    @ResponseBody
    public String method() {
        StringBuffer buffer = sensitiveService.filter("色情大师傅似来赌博的");
        System.out.println(buffer);
        return buffer.toString();
    }

}
