package com.chatapp.itey.controller;

import com.chatapp.itey.model.payload.LoginReq;
import com.chatapp.itey.model.payload.LoginResp;
import com.chatapp.itey.model.payload.UserReq;
import com.chatapp.itey.model.payload.UserResp;
import com.chatapp.itey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("v1/api/user")
public class UserController {
    @Autowired
    UserService userSrv;

    @PostMapping("/register")
    Mono<UserResp> createUser(@RequestBody UserReq userReq) throws ExecutionException, InterruptedException {
        return userSrv.createUser(userReq);
    }

    @GetMapping("/{id}")
    Mono<UserResp> getUserById(@PathVariable("id") String id) throws ExecutionException, InterruptedException {
        return userSrv.findUserById(id);
    }

    @PutMapping
    Mono<UserResp> editUser(@RequestBody UserReq userReq) throws ExecutionException, InterruptedException {
        return userSrv.editUser(userReq);
    }

    @PostMapping("/login")
    Mono<LoginResp> login(@RequestBody LoginReq loginReq) {
        return userSrv.loginUser(loginReq);
    }

    @GetMapping("/all")
    Mono<List<UserResp>> getAllUser() {
        return userSrv.getAllUsers();
    }

    @GetMapping("/strange")
    Mono<List<UserResp>> findUserNotFriendsByDisplayName(@RequestParam(required = true) String displayName) throws ExecutionException, InterruptedException {
        return userSrv.findUserNotFriendByDisplayName(displayName);
    }

    @GetMapping
    Mono<List<UserResp>> getUsersByDisplayName(@RequestParam String displayName) throws ExecutionException, InterruptedException {
        return userSrv.findUserByDisplayName(displayName);
    }



}
