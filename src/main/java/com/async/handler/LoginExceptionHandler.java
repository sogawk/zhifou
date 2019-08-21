package com.async.handler;

import com.async.EventHandler;
import com.async.EventModel;
import com.async.EventType;
import com.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    MailSender mailSender;

    @Override
    public void doHandler(EventModel model) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", model.getExt("username"));
        mailSender.sendWithHtmlTemplate(model.getExt("email"), "登陆成功", "mails/login_exception.html", map);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LOGIN);
    }
}
