package com.controller;

import com.bean.*;
import com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    FollowService followService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;
    private List<ViewObject> getQuestionList(int userId, int offset, int limit) {
        List<Question> list = questionService.selectQuestion(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : list) {
            ViewObject vo = new ViewObject();
            vo.put("question", question);
            vo.put("user", userService.getUserById(question.getUserId()));
            vo.put("followCount", followService.getFollowerCount(EntityType.Entity_Question, question.getUserId()));
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

    @RequestMapping("/user/{userId}")
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestionList(userId, 0, 10));

        User user = userService.getUserById(userId);
        ViewObject vo = new ViewObject();

        vo.put("user", user);
        vo.put("commentCount", commentService.getUserCommentCount(userId));
        vo.put("followerCount", followService.getFollowerCount(EntityType.Entity_User, userId));
        vo.put("followeeCount", followService.getFolloweeCount(userId, EntityType.Entity_User));
        if (hostHolder.getUser() != null) {
            vo.put("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.Entity_User, userId));
        } else {
            vo.put("followed", false);
        }
        model.addAttribute("profileUser", vo);
        return "profile";
    }



}
