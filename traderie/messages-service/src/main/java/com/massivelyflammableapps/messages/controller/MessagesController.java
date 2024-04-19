package com.massivelyflammableapps.messages.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.repository.MessageRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public String getMethodName(@RequestParam(required = false) String param) {
        return "yay you made it hehe";
    }

    @PostMapping
    public ResponseEntity<Message> postMethodName(@RequestBody Message request) {
        try {
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
            // HH:mm:ss");

            Message newOffer = new Message(
                    UUID.randomUUID(), request.getSenderId(), request.getReceiverId(), request.getMessageText(),
                    UUID.randomUUID());

            Message response = messageRepository.save(newOffer);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error response entity
            return ResponseEntity.status(500).build();
        }
    }
}