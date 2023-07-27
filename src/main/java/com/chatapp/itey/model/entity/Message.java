package com.chatapp.itey.model.entity;

import com.chatapp.itey.model.entity.modelType.MessageType;
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
    private String channelId;
    private String userSendId;
    private MessageType type;
    private String content;
    private LocalDateTime timeSent;
}
