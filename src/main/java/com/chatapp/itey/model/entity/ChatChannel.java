package com.chatapp.itey.model.entity;

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
public class ChatChannel {
    private ChannelType type;
    private String name;
    private List<User> users;
}
