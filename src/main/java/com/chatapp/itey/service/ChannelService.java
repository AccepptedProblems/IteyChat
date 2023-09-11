package com.chatapp.itey.service;

import com.chatapp.itey.model.payload.ChatChannelReq;
import com.chatapp.itey.model.payload.ChatChannelResp;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChannelService {
    Mono<ChatChannelResp> createChannel(ChatChannelReq chatChannelReq) throws ExecutionException, InterruptedException;
    Mono<ChatChannelResp> getChannelById(String channelId) throws ExecutionException, InterruptedException;
    Mono<List<ChatChannelResp>> getChannels() throws ExecutionException, InterruptedException;
    Mono<ChatChannelResp> getDirectChannelFromUserId(String userId) throws ExecutionException, InterruptedException;
    Mono<ChatChannelResp> deleteChannel(String channelId);
}
