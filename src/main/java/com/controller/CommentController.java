package com.controller;

import com.bean.Comment;
import com.bean.EntityType;
import com.bean.HostHolder;
import com.service.CommentService;
import com.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping("/addComment")
    public String addComment(@RequestParam("questionId") int questionId,
                             @RequestParam("content") String content) {
        content = HtmlUtils.htmlEscape(content);
        content = sensitiveService.filter(content).toString();
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateTime(new Date());
        comment.setEntity_type(EntityType.Entity_Question);
        comment.setEntityId(questionId);
        comment.setStatus(0);
        if (hostHolder.getUser() == null) {
            return "redirect:/";
        } else {
            comment.setUserId(hostHolder.getUser().getId());
        }

        commentService.addComment(comment);
        return "redirect:question/" + questionId ;
    }

}
