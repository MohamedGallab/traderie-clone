package com.massivelyflammableapps.messages.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.repository.ChatRepository;
import com.massivelyflammableapps.messages.repository.MessageRepository;
import com.massivelyflammableapps.messages.service.ChatService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<Chat> postChat(@RequestBody Chat request) {
        return chatService.postChat(request);
    }

    @PutMapping("/changeArchiveStatus")
    public ResponseEntity<Chat> changeArchiveStatus(@RequestParam UUID chatId) {
        return chatService.changeArchiveStatus(chatId);
    }

    @PutMapping("/changeAcceptStatus")
    public ResponseEntity<Chat> changeAcceptStatus(@RequestParam UUID chatId) {
        return chatService.changeAcceptStatus(chatId);
    }
}