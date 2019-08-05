package com.controller;

import com.bean.Question;
import com.bean.ViewObject;
import com.service.QuestionService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;
    private List<ViewObject> getQuestionList(int userId, int offset, int limit) {
        List<Question> list = questionService.selectQuestion(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : list) {
            ViewObject vo = new ViewObject();
            vo.put("question", question);
            vo.put("user", userService.getUserById(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping({"/","index"})
    public String index(Model model) {
        List<ViewObject> list = getQuestionList(0, 0, 10);
        model.addAttribute("vos", list);
        return "index";
    }

}
