package com.controller;

import com.bean.HostHolder;
import com.bean.Message;
import com.bean.User;
import com.bean.ViewObject;
import com.service.MessageService;
import com.service.SensitiveService;
import com.service.UserService;
import com.util.zhifouUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    SensitiveService sensitiveService;

    @RequestMapping("/addMessage")
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content) {
        User targetUser = userService.selectUserByName(toName);
        if (targetUser == null) {
            return zhifouUtil.getJsonString(1, "用户不存在");
        }

        if (hostHolder.getUser() == null) {
            return zhifouUtil.getJsonString(999, "未登录");
        }
        Message message = new Message();
        message.setContent(sensitiveService.filter(content).toString());
        message.setCreatedDate(new Date());
        message.setFromId(hostHolder.getUser().getId());
        message.setHasRead(0);
        message.setToId(targetUser.getId());
        message.setConversationId(
                hostHolder.getUser().getId() < targetUser.getId()
                        ? String.format("%d_%d", hostHolder.getUser().getId(),targetUser.getId())
                :String.format("%d_%d", targetUser.getId(), hostHolder.getUser().getId()));
        messageService.addMessage(message);
        return zhifouUtil.getJsonString(0);
    }

    @RequestMapping("/list")
    public String conversationList(Model model) {
        List<Message> messageList = messageService.getMessageList(hostHolder.getUser().getId(), 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Message message : messageList) {
            int id = message.getFromId() == hostHolder.getUser().getId() ? message.getToId() : message.getFromId();
            User targetUser = userService.getUserById(id);
            ViewObject vo = new ViewObject();
            vo.put("user", targetUser);
            vo.put("conversation", message);
            vo.put("unread", messageService.getUnReadId(hostHolder.getUser().getId(), message.getConversationId()));
            vos.add(vo);
        }
        model.addAttribute("conversations", vos);
        return "letter";
    }

    @RequestMapping("/detail")
    public String messageDetail(Model model, @RequestParam("conversationId") String conversationId) {
        List<Message> messageDetail = messageService.getMessageDetail(conversationId);
        List<ViewObject> vos = new ArrayList<>();
        for (Message message : messageDetail) {
            ViewObject vo = new ViewObject();
            vo.put("message", message);
            User user = userService.getUserById(message.getFromId());
            vo.put("userId", user.getId());
            vo.put("headUrl", user.getHeadUrl());
            vos.add(vo);
        }
        model.addAttribute("messages", vos);
        return "letterDetail";
    }
}
