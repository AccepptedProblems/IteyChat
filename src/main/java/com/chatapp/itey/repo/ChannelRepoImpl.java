package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.ChatChannel;
import com.chatapp.itey.model.payload.ChatChannelReq;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChannelRepoImpl implements ChannelRepo{
    @Override
    public ChatChannel addChannel(ChatChannelReq chatChannelReq) {
        return null;
    }

    @Override
    public List<ChatChannel> getChannelByUserId(String userId) {
        return null;
    }

    @Override
    public ChatChannel deleteChannel(String channelId) {
        return null;
    }
}
