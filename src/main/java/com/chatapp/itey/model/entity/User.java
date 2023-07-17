package com.chatapp.itey.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    private String username;
    private String password;
    private String email;
    private String displayName;
    private Gender gender;
    private String dayOfBirth;
}

