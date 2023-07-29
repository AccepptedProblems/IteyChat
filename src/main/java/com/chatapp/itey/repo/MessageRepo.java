package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.Message;
import com.chatapp.itey.model.payload.MessageReq;
import com.chatapp.itey.model.payload.MessageResp;

import java.util.List;

public interface MessageRepo {
    Message addMessage(MessageReq messageReq);
    List<Message> getMessagesFromChannel(String channelId);
    Message deleteMessage(String messageId);
}
