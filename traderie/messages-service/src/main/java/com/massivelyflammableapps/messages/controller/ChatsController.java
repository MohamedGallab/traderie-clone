package com.massivelyflammableapps.messages.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.repository.ChatRepository;
import com.massivelyflammableapps.messages.repository.MessageRepository;

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
    ChatRepository chatRepository;

    @GetMapping
    public List<Chat> getChatMessages(@RequestParam(required = true) UUID initiatorId,
            @RequestParam(required = true) UUID receiverId) {
        List<Chat> userChats = chatRepository.findByInitiatorIdAndReceiverId(initiatorId, receiverId);
        return userChats;
    }

    @GetMapping("/getUserChats")
    public List<Chat> getUserChats(@RequestParam(required = true) UUID userId) {
        List<Chat> chatsInitiator = chatRepository.findByInitiatorId(userId);
        List<Chat> chatsReceiver = chatRepository.findByReceiverId(userId);
        chatsInitiator.addAll(chatsReceiver);
        return chatsInitiator;
    }

    @PostMapping
    public ResponseEntity<Chat> postChat(@RequestBody Chat request) {
        try {
            Chat newChat = new Chat(request.getInitiatorId(), request.getReceiverId(), false);

            Chat response = chatRepository.save(newChat);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/changeArchiveStatus")
    public ResponseEntity<Chat> changeArchiveStatus(@RequestParam(required = true) UUID chatId) {
        Chat chatData = chatRepository.findByChatId(chatId);

        if (chatData == null) {
            return ResponseEntity.notFound().build();
        }

        chatData.setArchived(!chatData.isArchived());
        chatRepository.save(chatData);
        return ResponseEntity.ok(chatData);
    }

    @PutMapping("/changeAcceptStatus")
    public ResponseEntity<Chat> changeAcceptStatus(@RequestParam(required = true) UUID chatId) {
        Chat chatData = chatRepository.findByChatId(chatId);

        if (chatData == null) {
            return ResponseEntity.notFound().build();
        }

        chatData.setAccepted(!chatData.isAccepted());
        chatRepository.save(chatData);
        return ResponseEntity.ok(chatData);
    }
}