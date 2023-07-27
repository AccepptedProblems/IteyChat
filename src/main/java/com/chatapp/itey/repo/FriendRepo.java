package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.Relationship;
import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.entity.modelType.RelationshipStatus;
import com.chatapp.itey.model.payload.FriendResp;
import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.model.payload.UserResp;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FriendRepo {
    User addFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException;
    User confirmFriendRequest(String relationshipId) throws ExecutionException, InterruptedException;
    User deleteFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException;
    List<FriendResp> getFriends(String userId, RelationshipStatus status) throws ExecutionException, InterruptedException;
    Boolean checkFriendRequest(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException;
    Relationship getRelationshipByUserId(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException;
}
