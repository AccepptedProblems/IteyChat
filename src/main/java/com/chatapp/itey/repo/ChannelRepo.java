package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.ChatChannel;
import com.chatapp.itey.model.payload.ChatChannelReq;
import com.chatapp.itey.model.payload.UserResp;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChannelRepo {
    ChatChannel addChannel(ChatChannelReq chatChannelReq) throws ExecutionException, InterruptedException;
    List<UserResp> getUserInChannel(String channelId) throws ExecutionException, InterruptedException;
    List<ChatChannel> getChannelByUserId(String userId) throws ExecutionException, InterruptedException;
    List<ChatChannel> getChannelsContainIn(List<String> channelIds) throws ExecutionException, InterruptedException;
    ChatChannel deleteChannel(String channelId) throws ExecutionException, InterruptedException;
}
