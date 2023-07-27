package com.chatapp.itey.controller;

import com.chatapp.itey.model.payload.MessageResp;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("v1/api/message")
public class MessageController {
    @PostMapping
    Mono<MessageResp> sendMessage() {
        return null;
    }

    @GetMapping()
    Mono<List<MessageResp>> geMessagesFromChannel(@RequestParam String id) {
        return null;
    }

    @DeleteMapping("/{id}")
    Mono<MessageResp> deleteMessage(@PathVariable("id") String messId) {
        return null;
    }
}
