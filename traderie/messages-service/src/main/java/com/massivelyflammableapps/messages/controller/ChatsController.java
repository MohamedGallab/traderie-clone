package com.massivelyflammableapps.messages.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.messages.dto.ChatRequest;
import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.ChatByInitiatorAndReceiver;
import com.massivelyflammableapps.messages.service.ChatService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/chats")
public class ChatsController {

    @Autowired
    ChatService chatService;

    // @GetMapping
    // public List<Chat> getChatMessages(@RequestParam(required = true) UUID
    // initiatorId,
    // @RequestParam(required = true) UUID receiverId) {
    // return chatService.getChatMessages(initiatorId, receiverId);
    // }

    @GetMapping("/getUserChats")
    public List<Chat> getUserChats(@RequestParam UUID userId) {
        return chatService.getUserChats(userId);
    }

    @PostMapping
    public ResponseEntity<Chat> postChat(@RequestBody ChatRequest request) {
        try {
            Chat newChat = chatService.postChat(request);
            return ResponseEntity.ok(newChat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/requestChat")
    public ResponseEntity<ChatByInitiatorAndReceiver> requestChat(@RequestBody ChatRequest request) {
        try {
            ChatByInitiatorAndReceiver newChat = chatService.requestChat(request);
            return ResponseEntity.ok(newChat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/changeArchiveStatus")
    public ResponseEntity<Chat> changeArchiveStatus(@RequestParam UUID chatId) {
        try {
            Chat newChat = chatService.changeArchiveStatus(chatId);
            return ResponseEntity.ok(newChat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/changeAcceptStatus")
    public ResponseEntity<Chat> changeAcceptStatus(@RequestParam UUID chatId) {
        try {
            Chat newChat = chatService.changeAcceptStatus(chatId);
            return ResponseEntity.ok(newChat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}