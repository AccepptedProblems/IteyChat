package com.chatapp.itey.service;

import com.chatapp.itey.model.entity.ChatChannel;
import com.chatapp.itey.model.payload.ChatChannelReq;
import com.chatapp.itey.model.payload.ChatChannelResp;
import com.chatapp.itey.model.payload.MessageResp;
import com.chatapp.itey.model.payload.UserResp;
import com.chatapp.itey.repo.ChannelRepo;
import com.chatapp.itey.repo.MessageRepo;
import com.chatapp.itey.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ChannelSrvImpl implements ChannelService {
    @Autowired
    ChannelRepo channelRepo;

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public Mono<ChatChannelResp> createChannel(ChatChannelReq chatChannelReq) throws ExecutionException, InterruptedException {
        ChatChannel channel = channelRepo.addChannel(chatChannelReq);
        List<UserResp> users = userRepo.getUsersContainIn(chatChannelReq.getUserIds());
        return Mono.just(new ChatChannelResp(channel, null, users));
    }

    @Override
    public Mono<List<ChatChannelResp>> getUserChannels(String userId) throws ExecutionException, InterruptedException {
        List<ChatChannel> channels = channelRepo.getChannelByUserId(userId);
        System.out.println(channels);
        List<ChatChannelResp> chatChannelResps = channels.stream().map(channel -> {
            try {
                MessageResp message;
                if(channel.getLastMessageId() == null) message = null;
                else message = new MessageResp(messageRepo.findMessageById(channel.getLastMessageId()));
                List<UserResp> users = channelRepo.getUserInChannel(channel.getId());
                return new ChatChannelResp(channel, message, users);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        return Mono.just(chatChannelResps);
    }

    @Override
    public Mono<ChatChannelResp> deleteChannel(String channelId) {
        return null;
    }
}
