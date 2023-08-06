package com.chatapp.itey.service;

import com.chatapp.itey.model.payload.LoginReq;
import com.chatapp.itey.model.payload.LoginResp;
import com.chatapp.itey.model.payload.UserReq;
import com.chatapp.itey.model.payload.UserResp;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {
    Mono<UserResp> createUser(UserReq userReq) throws ExecutionException, InterruptedException;
    Mono<UserResp> editUser(UserReq userReq);
    Mono<LoginResp> loginUser(LoginReq loginReq);
    Mono<UserResp> findUserById(String id) throws ExecutionException, InterruptedException;
    Mono<UserResp> findUsersByUsername(String username) throws ExecutionException, InterruptedException;
    Mono<List<UserResp>> findUserByDisplayName(String displayName) throws ExecutionException, InterruptedException;
    Mono<List<UserResp>> findUserNotFriendByDisplayName(String displayName) throws ExecutionException, InterruptedException;
    Mono<List<UserResp>> getAllUsers();
}
