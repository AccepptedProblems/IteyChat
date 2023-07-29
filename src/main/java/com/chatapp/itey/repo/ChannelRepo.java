package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.ChatChannel;
import com.chatapp.itey.model.payload.ChatChannelReq;

import java.util.List;

public interface ChannelRepo {
    ChatChannel addChannel(ChatChannelReq chatChannelReq);
    List<ChatChannel> getChannelByUserId(String userId);
    ChatChannel deleteChannel(String channelId);
}
