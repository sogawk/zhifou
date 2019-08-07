package com.service;

import com.bean.Message;

import java.util.List;

public interface MessageService {
    int addMessage(Message message);

    List<Message> getMessageList(int userId, int offset, int limit);

    int getUnReadId(int toUserId, String conversationId);

    List<Message> getMessageDetail(String conversationId);
}
