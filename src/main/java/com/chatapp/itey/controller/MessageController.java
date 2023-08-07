package com.chatapp.itey.controller;

import com.chatapp.itey.model.payload.MessageReq;
import com.chatapp.itey.model.payload.MessageResp;
import com.chatapp.itey.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("v1/api/message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @PostMapping
    @MessageMapping("/chat")
    @SendTo("message/newMessage")
    Mono<MessageResp> sendMessage(@RequestBody MessageReq messageReq) throws ExecutionException, InterruptedException {
        return messageService.sendMessage(messageReq);
    }

    @GetMapping()
    Mono<List<MessageResp>> geMessagesFromChannel(@RequestParam String channelId) throws ExecutionException, InterruptedException {
        return messageService.getListMessage(channelId);
    }

    @DeleteMapping("/{id}")
    @MessageMapping("/chat")
    @SendTo("message/deleteMessage")
    Mono<MessageResp> deleteMessage(@PathVariable("id") String messId) throws ExecutionException, InterruptedException {
        return messageService.deleteMessage(messId);
    }
}
