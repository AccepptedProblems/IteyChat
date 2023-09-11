package com.chatapp.itey.repo;

import com.chatapp.itey.model.entity.User;
import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.model.payload.UserResp;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FriendRepo {
    User addFriend(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException;
    User confirmFriendRequest(String userId) throws ExecutionException, InterruptedException;
    User deleteFriend(String friendId) throws ExecutionException, InterruptedException;
    List<String> getFriendIds() throws ExecutionException, InterruptedException;
    List<UserResp> getFriends() throws ExecutionException, InterruptedException;
    List<UserResp> getFriendRequest() throws ExecutionException, InterruptedException;
    Boolean checkFriendRequest(RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException;
}
