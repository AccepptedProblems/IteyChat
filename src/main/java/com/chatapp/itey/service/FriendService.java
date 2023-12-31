package com.chatapp.itey.service;

import com.chatapp.itey.model.payload.FriendResp;
import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.model.payload.UserResp;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FriendService {
    Mono<UserResp> addFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException;
    Mono<List<UserResp>> friendList() throws ExecutionException, InterruptedException;
    Mono<List<UserResp>> friendRequestList() throws ExecutionException, InterruptedException;
    Mono<UserResp> deleteFriend(String friendId) throws ExecutionException, InterruptedException;
    Mono<UserResp> confirm(String relationshipId) throws ExecutionException, InterruptedException;
}
