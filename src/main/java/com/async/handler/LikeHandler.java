package com.async.handler;

import com.async.EventHandler;
import com.async.EventModel;
import com.async.EventType;
import com.bean.Message;
import com.bean.User;
import com.service.MessageService;
import com.service.UserService;
import com.util.zhifouUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel model) {
        Message message = new Message();
        message.setFromId(zhifouUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        message.setConversationId(String.format("%d_%d",message.getFromId(),message.getToId()));

        User user = userService.getUserById(model.getActorId());
        message.setContent("用户"+user.getName()+ "赞了你的评论，http://127.0.0.1:8080/question/" + model.getExt("questionId"));
        messageService.addMessage(message);

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
