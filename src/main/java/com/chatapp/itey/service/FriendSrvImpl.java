package com.chatapp.itey.service;

import com.chatapp.itey.model.entity.Relationship;
import com.chatapp.itey.model.entity.modelType.RelationshipStatus;
import com.chatapp.itey.model.payload.FriendResp;
import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.model.payload.UserResp;
import com.chatapp.itey.repo.FriendRepo;
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
        //TODO: Validate data
        if(friendRepo.checkFriendRequest(relationshipRequest)) {
            Relationship rela = friendRepo.getRelationshipByUserId(relationshipRequest);
            return Mono.just(new UserResp(friendRepo.confirmFriendRequest(rela.getId()), relationshipRequest.getUserToId()));
        } else {
            return Mono.just(new UserResp(friendRepo.addFriend(relationshipRequest), relationshipRequest.getUserToId()));
        }
    }

    @Override
    public Mono<List<FriendResp>> friendList(String userId) throws ExecutionException, InterruptedException {
        return Mono.just(friendRepo.getFriends(userId, RelationshipStatus.FRIEND));
    }

    @Override
    public Mono<List<FriendResp>> friendRequestList(String userId) throws ExecutionException, InterruptedException {
        return Mono.just(friendRepo.getFriends(userId, RelationshipStatus.PENDING));
    }

    @Override
    public Mono<UserResp> deleteFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        return Mono.just(new UserResp(friendRepo.deleteFriend(relationshipRequest), ""));
    }

    @Override
    public Mono<UserResp> confirm(String relationshipId) throws ExecutionException, InterruptedException {
        return Mono.just(new UserResp(friendRepo.confirmFriendRequest(relationshipId), ""));
    }
}
