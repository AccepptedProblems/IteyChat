package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.Message;
import com.chatapp.itey.model.payload.MessageReq;
import com.chatapp.itey.model.payload.MessageResp;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface MessageRepo {
    Message addMessage(MessageReq messageReq) throws ExecutionException, InterruptedException;
    List<Message> getMessagesFromChannel(String channelId) throws ExecutionException, InterruptedException;
    Message deleteMessage(String messageId) throws ExecutionException, InterruptedException;
}
