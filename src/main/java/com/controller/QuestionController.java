package com.controller;

import com.bean.*;
import com.service.CommentService;
import com.service.QuestionService;
import com.service.SensitiveService;
import com.util.zhifouUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.tags.HtmlEscapeTag;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("question")
public class QuestionController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    QuestionService questionService;

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    CommentService commentService;
    @RequestMapping("add")
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        title = sensitiveService.filter(title).toString();
        title = HtmlUtils.htmlEscape(title);
        content = sensitiveService.filter(content).toString();
        content = HtmlUtils.htmlEscape(content);

        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setCreateTime(new Date());
        question.setCommentCount(0);

        if (hostHolder.getUser() == null) {
//            question.setUserId(zhifouUtil.ANONYMOUS_USERID);匿名
            return zhifouUtil.getJsonString(999);//重定向至登陆
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
        List<Comment> comments = commentService.getCommentsByEntity(EntityType.Entity_Question, questionId);
        List<ViewObject> list = new ArrayList<>();
        for (Comment comment : comments) {
            ViewObject viewObject = new ViewObject();
            viewObject.put("comment", comment);
            viewObject.put("user", hostHolder.getUser());
            list.add(viewObject);
        }
        model.addAttribute("comments", list);
        return "detail";
    }
}
