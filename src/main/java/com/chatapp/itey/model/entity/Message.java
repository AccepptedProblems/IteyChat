package com.chatapp.itey.model.entity;

import com.chatapp.itey.model.entity.modelType.MessageType;
import com.chatapp.itey.model.payload.MessageReq;
import com.chatapp.itey.utils.IteyUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Message {
    private String id;
    private String channelId;
    private String userSendId;
    private MessageType type;
    private String content;
    private LocalDateTime timeSent;

    public Message(MessageReq messageReq) {
        this.id = IteyUtils.newUUID(messageReq.getChannelId()+messageReq.getUserSendId());
        this.channelId = messageReq.getChannelId();
        this.userSendId = messageReq.getUserSendId();
        this.type = messageReq.getType();
        this.content = messageReq.getContent();
        this.timeSent = messageReq.getTimeSent();
    }
}
