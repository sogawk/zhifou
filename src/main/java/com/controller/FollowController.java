package com.controller;

import com.async.EventModel;
import com.async.EventProducer;
import com.async.EventType;
import com.bean.*;
import com.service.CommentService;
import com.service.FollowService;
import com.service.QuestionService;
import com.service.UserService;
import com.util.zhifouUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/followUser"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {
        if (hostHolder == null) {
            return zhifouUtil.getJsonString(999);
        }

        boolean b = followService.follow(hostHolder.getUser().getId(), EntityType.Entity_User, userId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityType(EntityType.Entity_User)
                .setEntityOwnerId(userId)
        );
        return zhifouUtil.getJsonString(b ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.Entity_User)));

    }

    @Autowired
    EventProducer eventProducer;

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @RequestMapping("/unfollowUser")
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId) {
        if (hostHolder == null) {
            return zhifouUtil.getJsonString(999);
        }

        boolean b = followService.unfollow(hostHolder.getUser().getId(), EntityType.Entity_User, userId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityType(EntityType.Entity_User)
                .setEntityOwnerId(userId)
        );
        return zhifouUtil.getJsonString(b ? 0 : 1, String.valueOf(followService.getFolloweeCount(hostHolder.getUser().getId(), EntityType.Entity_User)));

    }

    @RequestMapping("/followQuestion")
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId) {
        if (hostHolder == null) {
            return zhifouUtil.getJsonString(999);
        }

        Question question = questionService.selectQuestionById(questionId);
        if (question == null) {
            return zhifouUtil.getJsonString(1, "问题不存在");
        }
        boolean b = followService.follow(hostHolder.getUser().getId(), EntityType.Entity_Question, questionId);
        eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityType(EntityType.Entity_Question).setEntityId(questionId)
                .setEntityOwnerId(question.getUserId())
        );

        Map<String, Object> map = new HashMap<>();
        map.put("headUrl", hostHolder.getUser().getHeadUrl());
        map.put("name", hostHolder.getUser().getName());
        map.put("id", hostHolder.getUser().getId());
        map.put("count", followService.getFollowerCount(EntityType.Entity_Question, questionId));
        return zhifouUtil.getJsonString(b ? 0 : 1, map);

    }

    @RequestMapping("/unfollowQuestion")
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId) {
        if (hostHolder == null) {
            return zhifouUtil.getJsonString(999);
        }

        Question question = questionService.selectQuestionById(questionId);
        if (question == null) {
            return zhifouUtil.getJsonString(1, "问题不存在");
        }
        boolean b = followService.unfollow(hostHolder.getUser().getId(), EntityType.Entity_Question, questionId);

        eventProducer.fireEvent(new EventModel(EventType.UNFOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityType(EntityType.Entity_Question).setEntityId(questionId)
                .setEntityOwnerId(question.getUserId())
        );

        Map<String, Object> map = new HashMap<>();
        map.put("id", hostHolder.getUser().getId());
        map.put("count", followService.getFollowerCount(EntityType.Entity_Question, questionId));
        return zhifouUtil.getJsonString(b ? 0 : 1, map);

    }

    @RequestMapping("/user/{uid}/followers")
    public String followers(Model model, @PathVariable("uid") int userId) {
        List<Integer> followers = followService.getFollowers(EntityType.Entity_User, userId, 0, 10);
        if (hostHolder.getUser() == null) {
            model.addAttribute("followers", getUsersInfo(0, followers));
        } else {
            model.addAttribute("followers", getUsersInfo(hostHolder.getUser().getId(), followers));
        }
        model.addAttribute("followerCount", followService.getFollowerCount(EntityType.Entity_User, userId));
        model.addAttribute("curUser", userService.getUserById(userId));
        return "followers";
    }

    @RequestMapping("/user/{uid}/followees")
    public String followees(Model model, @PathVariable("uid") int userId) {
        List<Integer> followees = followService.getFollowees(userId, EntityType.Entity_User, 0, 10);

        if (hostHolder.getUser() == null) {
            model.addAttribute("followees", getUsersInfo(0, followees));
        } else {
            model.addAttribute("followees", getUsersInfo(hostHolder.getUser().getId(), followees));
        }
        model.addAttribute("followeeCount", followService.getFolloweeCount(userId, EntityType.Entity_User));
        model.addAttribute("curUser", userService.getUserById(userId));
        return "followees";
    }

    private List<ViewObject> getUsersInfo(int localUserId, List<Integer> userIds) {
        List<ViewObject> list = new ArrayList<>();
        for (Integer userId : userIds) {
            ViewObject vo = new ViewObject();

            User user = userService.getUserById(userId);
            vo.put("user", user);
            vo.put("commentCount", commentService.getUserCommentCount(userId));
            vo.put("followerCount", followService.getFollowerCount(EntityType.Entity_User, userId));
            vo.put("followeeCount", followService.getFolloweeCount(userId, EntityType.Entity_User));

            if (localUserId != 0) {
                vo.put("followed", followService.isFollower(localUserId, EntityType.Entity_User, userId));
            } else {
                vo.put("followed", false);
            }
            list.add(vo);
        }
        return list;
    }








}
