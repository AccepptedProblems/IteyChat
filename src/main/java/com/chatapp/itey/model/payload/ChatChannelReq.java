package com.chatapp.itey.model.payload;

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
public class ChatChannelReq {
    private ChannelType type;
    private String name;
    private List<String> userIds;
}
