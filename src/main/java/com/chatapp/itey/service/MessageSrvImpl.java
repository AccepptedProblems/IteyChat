package com.chatapp.itey.service;

import com.chatapp.itey.model.payload.MessageReq;
import com.chatapp.itey.model.payload.MessageResp;
import com.chatapp.itey.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MessageSrvImpl implements MessageService{
    @Autowired
    MessageRepo messageRepo;

    @Override
    public Mono<MessageResp> sendMessage(MessageReq messageReq) {
        return Mono.just(new MessageResp(messageRepo.addMessage(messageReq)));
    }

    @Override
    public Mono<List<MessageResp>> getListMessage(String channelId) {
        List<MessageResp> messages = messageRepo.getMessagesFromChannel(channelId).stream()
                .map(MessageResp::new).toList();
        return Mono.just(messages);
    }

    @Override
    public Mono<MessageResp> deleteMessage(String messageId) {
        return Mono.just(new MessageResp(messageRepo.deleteMessage(messageId)));
    }
}
