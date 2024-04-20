package com.massivelyflammableapps.messages.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.repository.ChatRepository;
import com.massivelyflammableapps.messages.repository.MessageRepository;
import com.massivelyflammableapps.messages.service.MessageService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    MessageService messageService;

    @GetMapping
    public List<Message> getChatMessages(@RequestParam(required = true) UUID chatId) {
        return messageService.getChatMessages(chatId);
    }

    @PostMapping
    public ResponseEntity<Message> postMessage(@RequestBody Message request) {
        return messageService.postMessage(request);
    }

}