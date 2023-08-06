package com.chatapp.itey.utils;

import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.model.payload.UserReq;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static void userSignInInformationValidate(UserReq userReq) {
        if (userReq.getUsername().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username cannot be an empty string");
        }
        if (userReq.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email cannot be an empty string");
        }
        if (isEmail(userReq.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is invalid");
        }

    }

    static boolean isEmail(String text) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(text);
        return matcher.matches();
    }

    public static void validFriendRequest(RelationshipRequest relationshipRequest) {
        if(relationshipRequest.getUserToId().equals(relationshipRequest.getUserFromId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot send friend request to yourself");
        }
        if (relationshipRequest.getUserToId().isEmpty() || relationshipRequest.getUserFromId().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId must not be empty");
        }
    }
}
