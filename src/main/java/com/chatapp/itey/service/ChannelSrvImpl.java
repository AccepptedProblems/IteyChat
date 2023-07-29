package com.chatapp.itey.service;

import com.chatapp.itey.model.payload.ChatChannelReq;
import com.chatapp.itey.model.payload.ChatChannelResp;
import com.chatapp.itey.repo.ChannelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ChannelSrvImpl implements ChannelService {
    @Autowired
    ChannelRepo channelRepo;

    @Override
    public Mono<ChatChannelResp> createChannel(ChatChannelReq chatChannelReq) {
        return null;
    }

    @Override
    public Mono<List<ChatChannelResp>> getUserChannels(String userId) {
        return null;
    }

    @Override
    public Mono<ChatChannelResp> deleteChannel(String channelId) {
        return null;
    }
}
