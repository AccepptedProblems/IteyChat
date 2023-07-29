package com.chatapp.itey.controller;

import com.chatapp.itey.model.payload.ChatChannelResp;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("v1/api/channel")
public class ChannelController {
    @PostMapping
    Mono<ChatChannelResp> createChannel() {
        return null;
    }

    @GetMapping
    Mono<List<ChatChannelResp>> getUserChannel() {
        return null;
    }

    @DeleteMapping
    Mono<ChatChannelResp> deleteChannel() {
        return null;
    }
}
