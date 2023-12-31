package com.chatapp.itey.controller;

import com.chatapp.itey.model.payload.FriendResp;
import com.chatapp.itey.model.payload.RelationshipRequest;
import com.chatapp.itey.model.payload.UserResp;
import com.chatapp.itey.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("v1/api/friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @PostMapping
    Mono<UserResp> addFriend(@RequestBody RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        return friendService.addFriend(relationshipRequest);
    }

    @DeleteMapping("/{id}")
    Mono<UserResp> deleteFriend(@PathVariable("id") String friendId) throws ExecutionException, InterruptedException {
        return friendService.deleteFriend(friendId);
    }

    @GetMapping
    Mono<List<UserResp>> getFriends() throws ExecutionException, InterruptedException {
        return friendService.friendList();
    }

    @GetMapping("/request")
    Mono<List<UserResp>> getFriendRequests() throws ExecutionException, InterruptedException {
        return friendService.friendRequestList();
    }

    @PutMapping("/{id}/confirm")
    Mono<UserResp> confirmFriendRequest(@PathVariable("id") String userId) throws ExecutionException, InterruptedException {
        return friendService.confirm(userId);
    }
}
