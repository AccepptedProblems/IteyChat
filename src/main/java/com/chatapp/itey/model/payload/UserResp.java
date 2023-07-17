package com.chatapp.itey.model.payload;

import com.chatapp.itey.model.entity.Gender;
import com.chatapp.itey.model.entity.User;
import lombok.*;

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

    public UserResp(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.displayName = user.getDisplayName();
        this.gender = user.getGender();
        this.dayOfBirth = user.getDayOfBirth();
    }
}

