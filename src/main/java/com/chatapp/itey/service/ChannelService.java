package com.chatapp.itey.service;

import com.chatapp.itey.model.payload.ChatChannelReq;
import com.chatapp.itey.model.payload.ChatChannelResp;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ChannelService {
    Mono<ChatChannelResp> createChannel(ChatChannelReq chatChannelReq);
    Mono<List<ChatChannelResp>> getUserChannels(String userId);
    Mono<ChatChannelResp> deleteChannel(String channelId);
}
