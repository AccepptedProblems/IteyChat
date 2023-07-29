package com.chatapp.itey.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InChatChannel {
    private String id;
    private String channelId;
    private String userId;

    public InChatChannel(String channelId, String userId) {
        this.id = channelId + userId;
        this.channelId = channelId;
        this.userId = userId;
    }
}
