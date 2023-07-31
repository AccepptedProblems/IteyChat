package com.chatapp.itey.model.payload;

import com.chatapp.itey.model.entity.Message;
import com.chatapp.itey.model.entity.modelType.MessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageResp {
    private String id;
    private String channelId;
    private String userSendId;
    private MessageType type;
    private String content;
    private Date timeSent;

    public MessageResp(Message message) {
        this.id = message.getId();
        this.channelId = message.getChannelId();
        this.userSendId = message.getUserSendId();
        this.type = message.getType();
        this.content = message.getContent();
        this.timeSent = message.getTimeSent();
    }
}
