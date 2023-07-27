package com.chatapp.itey.service;

import com.chatapp.itey.model.payload.FriendResp;
import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.model.payload.UserResp;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FriendService {
    Mono<UserResp> addFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException;
    Mono<List<FriendResp>> friendList(String userId) throws ExecutionException, InterruptedException;
    Mono<List<FriendResp>> friendRequestList(String userId) throws ExecutionException, InterruptedException;
    Mono<UserResp> deleteFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException;
    Mono<UserResp> confirm(String relationshipId) throws ExecutionException, InterruptedException;
}
