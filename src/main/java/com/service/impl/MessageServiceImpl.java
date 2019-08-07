package com.service.impl;

import com.bean.Message;
import com.mapper.MessageMapper;
import com.service.MessageService;
import com.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public int addMessage(Message message) {
        return messageMapper.insertMessage(message);
    }

    @Override
    public List<Message> getMessageList(int userId, int offset, int limit) {
        return messageMapper.selectByUserId(userId, offset, limit);
    }

    @Override
    public int getUnReadId(int toUserId, String conversationId) {
        return messageMapper.selectUnreadCount(toUserId, conversationId);
    }

    @Override
    public List<Message> getMessageDetail(String conversationId) {
        return messageMapper.selectByConversationId(conversationId);
    }
}
