package com.chatapp.itey.service;

import com.chatapp.itey.model.entity.ChatChannel;
import com.chatapp.itey.model.entity.Message;
import com.chatapp.itey.model.entity.modelType.ChannelType;
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
        channel.setName(channelRepo.getChannelName(channel));
        List<UserResp> users = userRepo.getUsersContainIn(chatChannelReq.getUserIds());
        return Mono.just(new ChatChannelResp(channel, null, users));
    }

    @Override
    public Mono<ChatChannelResp> getChannelById(String channelId) throws ExecutionException, InterruptedException {
        ChatChannel channel = channelRepo.getChannelById(channelId);
        channel.setName(channelRepo.getChannelName(channel));
        List<UserResp> users = channelRepo.getUserInChannel(channelId);
        Message message = messageRepo.getMessagesFromChannel(channelId).get(0);
        return Mono.just(new ChatChannelResp(channel, new MessageResp(message), users));
    }

    @Override
    public Mono<List<ChatChannelResp>> getUserChannels(String userId) throws ExecutionException, InterruptedException {
        List<ChatChannel> channels = channelRepo.getChannelsByUserId(userId);
        System.out.println(channels);
        List<ChatChannelResp> chatChannelResps = channels.stream().map(channel -> {
            try {
                ChatChannel finalChannel = channel;
                finalChannel.setName(channelRepo.getChannelName(channel));
                MessageResp message;
                if(channel.getLastMessageId() == null) message = null;
                else {
                    Message mess = messageRepo.findMessageById(channel.getLastMessageId());
                    if (mess == null) message = null;
                    else message = new MessageResp(mess);
                }

                List<UserResp> users = channelRepo.getUserInChannel(channel.getId());
                return new ChatChannelResp(finalChannel, message, users);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).toList();
        return Mono.just(chatChannelResps);
    }

    @Override
    public Mono<ChatChannelResp> getDirectChannelFromUserId(String userId) throws ExecutionException, InterruptedException {
        String currentUserId = UserResp.currentUser().getId();
        List<ChatChannel> channels = channelRepo.getChannelsByUserId(currentUserId);

        List<ChatChannel> results = channels.stream().filter(value -> {
            try {
                List<String> users = channelRepo.getUserInChannel(value.getId()).stream().map(UserResp::getId).toList();
                return users.contains(userId) && value.getType() == ChannelType.DIRECT;
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        }).toList();
        if (results.isEmpty()){
            return createChannel(new ChatChannelReq(ChannelType.DIRECT, "", List.of(currentUserId, userId)));
        }
        ChatChannel channel = results.get(0);
        channel.setName(channelRepo.getChannelName(channel));
        MessageResp message;
        List<Message> messes = messageRepo.getMessagesFromChannel(channel.getId());
        if(!messes.isEmpty()) message = new MessageResp(messes.get(0));
        else message = null;
        List<UserResp> users = channelRepo.getUserInChannel(channel.getId());
        return Mono.just(new ChatChannelResp(channel, message, users));
    }

    @Override
    public Mono<ChatChannelResp> deleteChannel(String channelId) {
        return null;
    }
}
