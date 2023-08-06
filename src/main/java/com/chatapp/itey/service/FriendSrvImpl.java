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
            Relationship rela = friendRepo.getRelationshipByUserId(relationshipRequest);
            return Mono.just(new UserResp(friendRepo.confirmFriendRequest(rela.getId()), relationshipRequest.getUserToId()));
        } else {
            return Mono.just(new UserResp(friendRepo.addFriend(relationshipRequest), relationshipRequest.getUserToId()));
        }
    }

    @Override
    public Mono<List<FriendResp>> friendList(String userId) throws ExecutionException, InterruptedException {
        return Mono.just(friendRepo.getFriends(userId));
    }

    @Override
    public Mono<List<FriendResp>> friendRequestList(String userId) throws ExecutionException, InterruptedException {
        return Mono.just(friendRepo.getFriendRequest(userId));
    }

    @Override
    public Mono<UserResp> deleteFriend(String friendId) throws ExecutionException, InterruptedException {
        return Mono.just(new UserResp(friendRepo.deleteFriend(friendId), ""));
    }

    @Override
    public Mono<UserResp> confirm(String relationshipId) throws ExecutionException, InterruptedException {
        return Mono.just(new UserResp(friendRepo.confirmFriendRequest(relationshipId), ""));
    }
}
