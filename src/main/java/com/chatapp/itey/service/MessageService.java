package com.chatapp.itey.service;

import com.chatapp.itey.model.payload.MessageReq;
import com.chatapp.itey.model.payload.MessageResp;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface MessageService {
    Mono<MessageResp> sendMessage(MessageReq messageReq) throws ExecutionException, InterruptedException;
    Mono<List<MessageResp>> getListMessage(String channelId) throws ExecutionException, InterruptedException;
    Mono<MessageResp> deleteMessage(String messageId) throws ExecutionException, InterruptedException;
}
