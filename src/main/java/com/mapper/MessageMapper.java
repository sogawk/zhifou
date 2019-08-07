package com.mapper;

import com.bean.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageMapper {
    int insertMessage(Message message);

    List<Message> selectByUserId(int userId, int offset, int limit);

    List<Message> selectByConversationId(String conversationId);

    int selectUnreadCount(int toUserId, String conversationId);
}
