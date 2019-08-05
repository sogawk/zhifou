package com.controller;

import com.bean.HostHolder;
import com.bean.Question;
import com.service.QuestionService;
import com.util.zhifouUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("question")
public class QuestionController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    @RequestMapping("add")
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam ("content") String content) {
        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setCreateTime(new Date());
        question.setCommentCount(0);

        if (hostHolder.getUser() == null) {
//            question.setUserId(zhifouUtil.ANONYMOUS_USERID);
            return zhifouUtil.getJsonString(999);
        } else {
            question.setUserId(hostHolder.getUser().getId());
        }

        int result = questionService.addQuestion(question);
        if (result > 0) {
            return zhifouUtil.getJsonString(0);
        } else {
            return zhifouUtil.getJsonString(-1, "失败");
        }

    }

    @RequestMapping("/{questionId}")
    public String selectQuestion(@PathVariable("questionId") int questionId,
                                 Model model) {
        Question question = questionService.selectQuestionById(questionId);
        model.addAttribute("question", question);
        return "detail";
    }
}
