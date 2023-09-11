package com.chatapp.itey.model.entity;

import com.chatapp.itey.model.entity.modelType.Gender;
import com.chatapp.itey.model.payload.UserReq;
import com.chatapp.itey.utils.IteyUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private static User currentUser;

    private String username;
    private String password;
    private String email;
    private String displayName;
    private Gender gender;
    private String dayOfBirth;
    private List<String> friendIds;
    private List<String> friendRequestIds;

    public User(UserReq userReq) {
        this.username = IteyUtils.newUUID(userReq.getEmail());
        this.password = userReq.getPassword();
        this.email = userReq.getEmail();
        this.displayName = userReq.getDisplayName();
        this.gender = userReq.getGender();
        this.dayOfBirth = userReq.getDayOfBirth().toString();
        this.friendRequestIds = List.of();
        this.friendIds = List.of();
    }


}

