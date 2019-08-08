package com.controller;

import com.bean.Comment;
import com.bean.EntityType;
import com.bean.HostHolder;
import com.bean.User;
import com.service.CommentService;
import com.service.LikeService;
import com.util.zhifouUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @RequestMapping("/like")
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        User user = hostHolder.getUser();
        if (user == null) {
            return zhifouUtil.getJsonString(999, "未登录");
        }

        long likeCount = likeService.like(user.getId(), EntityType.Entity_Comment, commentId);
        return zhifouUtil.getJsonString(0, String.valueOf(likeCount));
    }

    @RequestMapping("/dislike")
    @ResponseBody
    public String disLike(@RequestParam("commentId") int commentId) {
        User user = hostHolder.getUser();
        if (user == null) {
            return zhifouUtil.getJsonString(999, "未登录");
        }

        long likeCount = likeService.disLike(user.getId(), EntityType.Entity_Comment, commentId);
        return zhifouUtil.getJsonString(0, String.valueOf(likeCount));
    }


}
