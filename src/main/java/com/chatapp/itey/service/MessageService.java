package com.chatapp.itey.service;

import com.chatapp.itey.model.payload.MessageReq;
import com.chatapp.itey.model.payload.MessageResp;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MessageService {
    Mono<MessageResp> sendMessage(MessageReq messageReq);
    Mono<List<MessageResp>> getListMessage(String channelId);
    Mono<MessageResp> deleteMessage(String messageId);
}
