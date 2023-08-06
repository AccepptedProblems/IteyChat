package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.payload.LoginResp;
import com.chatapp.itey.model.payload.UserReq;
import com.chatapp.itey.model.payload.UserResp;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserRepo {
    User createUser(UserReq userReq, String id) throws ExecutionException, InterruptedException;
    User updateUser(UserReq userReq);
    User findById(String id) throws ExecutionException, InterruptedException;
    User findByEmail(String email) throws ExecutionException, InterruptedException;
    UserResp findUserRespByEmail(String email) throws ExecutionException, InterruptedException;
    UserResp findByUsername(String username) throws ExecutionException, InterruptedException;
    User findUserByUsername(String username) throws ExecutionException, InterruptedException;
    List<UserResp> findByDisplayName(String displayName) throws ExecutionException, InterruptedException;
    List<UserResp> getUsersContainIn(List<String> userIds);
    List<UserResp> getAll();
}
