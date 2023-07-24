package com.chatapp.itey.model.payload;

import com.chatapp.itey.model.entity.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReq {
    private String username;
    private String password;
    private String email;
    private String displayName;
    private Gender gender;
    private LocalDate dayOfBirth;
}
