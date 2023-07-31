package com.chatapp.itey.model.entity;

import com.chatapp.itey.utils.IteyUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChannelName {
    private String id;
    private String channelId;
    private String userId;
    private String name;

    public ChannelName(String channelId, String userId, String name) {
        this.id = IteyUtils.newUUID("");
        this.channelId = channelId;
        this.userId = userId;
        this.name = name;
    }
}
