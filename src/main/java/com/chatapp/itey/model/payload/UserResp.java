package com.chatapp.itey.model.payload;

import com.chatapp.itey.model.entity.Gender;
import com.chatapp.itey.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResp {
    private String id;
    private String username;
    private String password;
    private String email;
    private String displayName;
    private Gender gender;
    private String dayOfBirth;

    public UserResp(User user, String id) {
        this.id = id;
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.displayName = user.getDisplayName();
        this.gender = user.getGender();
        this.dayOfBirth = user.getDayOfBirth();
    }
}

