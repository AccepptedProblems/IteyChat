package com.chatapp.itey.model.payload;

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
@Getter
@Setter
public class MessageReq {
    private String channelId;
    private String userSendId;
    private MessageType type;
    private String content;
    private Date timeSent;
}
