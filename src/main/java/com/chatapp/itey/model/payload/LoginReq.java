package com.chatapp.itey.model.payload;

import com.chatapp.itey.model.entity.ChatChannel;
import com.chatapp.itey.model.entity.Message;
import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.entity.modelType.ChannelType;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class LoginReq {
    private String username;
    private String password;
}
