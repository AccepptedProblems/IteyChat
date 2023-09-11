package com.chatapp.itey.service;

import com.chatapp.itey.model.entity.Relationship;
import com.chatapp.itey.model.payload.FriendResp;
import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.model.payload.UserResp;
import com.chatapp.itey.repo.FriendRepo;
import com.chatapp.itey.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FriendSrvImpl implements FriendService{
    @Autowired
    FriendRepo friendRepo;
    
    @Override
    public Mono<UserResp> addFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        Validator.validFriendRequest(relationshipRequest);
        if(friendRepo.checkFriendRequest(relationshipRequest)) {
            return Mono.just(new UserResp(friendRepo.confirmFriendRequest(relationshipRequest.getUserId()), relationshipRequest.getUserId()));
        } else {
            return Mono.just(new UserResp(friendRepo.addFriend(relationshipRequest), relationshipRequest.getUserId()));
        }
    }

    @Override
    public Mono<List<UserResp>> friendList() throws ExecutionException, InterruptedException {
        return Mono.just(friendRepo.getFriends());
    }

    @Override
    public Mono<List<UserResp>> friendRequestList() throws ExecutionException, InterruptedException {
        return Mono.just(friendRepo.getFriendRequest());
    }

    @Override
    public Mono<UserResp> deleteFriend(String userId) throws ExecutionException, InterruptedException {
        return Mono.just(new UserResp(friendRepo.deleteFriend(userId), userId));
    }

    @Override
    public Mono<UserResp> confirm(String userId) throws ExecutionException, InterruptedException {
        return Mono.just(new UserResp(friendRepo.confirmFriendRequest(userId), userId));
    }
}
