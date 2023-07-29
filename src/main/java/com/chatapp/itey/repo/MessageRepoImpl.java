package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.Message;
import com.chatapp.itey.model.payload.MessageReq;
import com.google.cloud.firestore.Firestore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageRepoImpl implements  MessageRepo{
    @Autowired
    Firestore firestore;

    @Override
    public Message addMessage(MessageReq messageReq) {

        return null;
    }

    @Override
    public List<Message> getMessagesFromChannel(String channelId) {
        return null;
    }

    @Override
    public Message deleteMessage(String messageId) {
        return null;
    }
}
