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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/messages")
public class MessagesController {

    @Autowired
    MessageRepository messageRepository;

    @GetMapping
    public List<Message> getChatMessages(@RequestParam(required = true) UUID chatId) {
        List<Message> chatMessages = messageRepository.findByChatId(chatId);
        return chatMessages;
    }

    @PostMapping
    public ResponseEntity<Message> postMessage(@RequestBody Message request) {
        try {
            Message newMessage = new Message(request.getSenderId(), request.getReceiverId(), request.getMessageText(),
                    request.getChatId());

            Message response = messageRepository.save(newMessage);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error response entity
            return ResponseEntity.status(500).build();
        }
    }

}