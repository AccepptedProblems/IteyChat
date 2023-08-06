package com.chatapp.itey.controller;

import com.chatapp.itey.model.payload.ChatChannelReq;
import com.chatapp.itey.model.payload.ChatChannelResp;
import com.chatapp.itey.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("v1/api/channel")
public class ChannelController {
    @Autowired
    ChannelService channelService;

    @PostMapping
    Mono<ChatChannelResp> createChannel(@RequestBody ChatChannelReq chatChannelReq) throws ExecutionException, InterruptedException {
        return channelService.createChannel(chatChannelReq);
    }

    @GetMapping
    Mono<List<ChatChannelResp>> getUserChannel(@RequestParam String userId) throws ExecutionException, InterruptedException {
        return channelService.getUserChannels(userId);
    }

    @GetMapping("/direct")
    Mono<ChatChannelResp> getDirectChannelFromUserId(@RequestParam String userId) throws ExecutionException, InterruptedException {
        return channelService.getDirectChannelFromUserId(userId);
    }

    @DeleteMapping
    Mono<ChatChannelResp> deleteChannel() {
        return null;
    }
}
