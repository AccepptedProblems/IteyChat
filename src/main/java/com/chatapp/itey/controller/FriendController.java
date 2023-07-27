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

    @DeleteMapping
    Mono<UserResp> deleteFriend(@RequestBody RelationshipRequest relationshipRequest) throws ExecutionException, InterruptedException {
        return friendService.deleteFriend(relationshipRequest);
    }

    @GetMapping("/{id}/list")
    Mono<List<FriendResp>> getFriends(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        return friendService.friendList(id);
    }

    @GetMapping("/{id}/request")
    Mono<List<FriendResp>> getFriendRequests(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        return friendService.friendRequestList(id);
    }

    @PutMapping("/{id}/confirm")
    Mono<UserResp> confirmFriendRequest(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        return friendService.confirm(id);
    }
}
