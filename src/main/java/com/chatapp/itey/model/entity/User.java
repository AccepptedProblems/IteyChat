package com.chatapp.itey.model.entity;

import com.chatapp.itey.model.entity.modelType.Gender;
import com.chatapp.itey.model.payload.UserReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public User(UserReq userReq) {
        this.username = userReq.getUsername();
        this.password = userReq.getPassword();
        this.email = userReq.getEmail();
        this.displayName = userReq.getDisplayName();
        this.gender = userReq.getGender();
        this.dayOfBirth = userReq.getDayOfBirth().toString();
    }


}

