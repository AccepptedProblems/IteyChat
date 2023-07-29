package com.chatapp.itey.model.payload;

import com.chatapp.itey.model.entity.ChatChannel;
import com.chatapp.itey.model.entity.Message;
import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.entity.modelType.ChannelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatChannelResp {
    private String id;
    private ChannelType type;
    private String name;
    private Message latestMess;
    private List<UserResp> users;

    public ChatChannelResp(ChatChannel chatChannel, Message message, String id, List<UserResp> users) {
        this.id = id;
        this.type = chatChannel.getType();
        this.name = chatChannel.getName();
        this.latestMess = message;
        this.users = users;
    }
}