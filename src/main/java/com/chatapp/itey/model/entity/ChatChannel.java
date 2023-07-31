package com.chatapp.itey.model.entity;

import com.chatapp.itey.model.entity.modelType.ChannelType;
import com.chatapp.itey.model.payload.ChatChannelReq;
import com.chatapp.itey.utils.IteyUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatChannel {
    private String id;
    private ChannelType type;
    private String name;
    private String lastMessageId;

    public ChatChannel(ChatChannelReq chatChannelReq) {
        this.id = IteyUtils.newUUID(chatChannelReq.getType().toString() + chatChannelReq.getName());
        this.type = chatChannelReq.getType();
        this.name = chatChannelReq.getName();
        this.lastMessageId = null;
    }
}
