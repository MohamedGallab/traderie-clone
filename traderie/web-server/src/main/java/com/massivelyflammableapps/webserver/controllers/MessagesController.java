package com.massivelyflammableapps.webserver.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.shared.dto.messages.chats.*;
import com.massivelyflammableapps.shared.dto.messages.messages.*;
import com.massivelyflammableapps.shared.dto.messages.*;
import com.massivelyflammableapps.shared.dto.messages.chats.ChatRequest;

@RestController
@RequestMapping("api/v1/messages")
public class MessagesController {
    @Value("${offers-service.queue.name}")
    private String messagesQueueName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    
    @GetMapping()
    public ResponseEntity<List<MessageDTO>> getChatMessages(@RequestBody MessageDTO request){
        try{
            GetChatMessagesRequest command = new GetChatMessagesRequest(request);
            List<MessageDTO> messages = rabbitTemplate.convertSendAndReceiveAsType("",messagesQueueName, command,
                    new ParameterizedTypeReference<List<MessageDTO>>() {
                    });
            return ResponseEntity.ok(messages);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


    @PostMapping()
    public ResponseEntity<MessageDTO> postMessage(@RequestBody MessageDTO request){
        try{
            PostMessageRequest command = new PostMessageRequest(request);
            MessageDTO message = rabbitTemplate.convertSendAndReceiveAsType("",messagesQueueName, command,
                    new ParameterizedTypeReference<MessageDTO>() {
                    });
            return ResponseEntity.ok(message);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(params = {"userId"})
    public ResponseEntity<List<ChatDTO>> getUserChats(@RequestParam UUID userId) {
        try {
            GetUserChatsRequest command = new GetUserChatsRequest(userId);
            List<ChatDTO> chats = rabbitTemplate.convertSendAndReceiveAsType("", messagesQueueName,
                    command,
                    new ParameterizedTypeReference<List<ChatDTO>>() {
                    });
            return ResponseEntity.ok(chats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping()
    public ResponseEntity<ChatDTO> postChat(@RequestBody ChatRequest request) {
        try {
            PostChatRequest command = new PostChatRequest(request);
            ChatDTO chat = rabbitTemplate.convertSendAndReceiveAsType("", messagesQueueName,
                    command,
                    new ParameterizedTypeReference<ChatDTO>() {
                    });
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping()
    public ResponseEntity<ChatDTO> requestChat(@RequestBody ChatRequest request) {
        try {
            RequestChatRequest command = new RequestChatRequest(request);
            ChatDTO chat = rabbitTemplate.convertSendAndReceiveAsType("", messagesQueueName,
                    command,
                    new ParameterizedTypeReference<ChatDTO>() {
                    });
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping(params = {"chatId"})
    public ResponseEntity<ChatDTO> changeArchiveStatus(@RequestParam UUID chatId) {
        try {
            ChangeArchiveStatusRequest command = new ChangeArchiveStatusRequest(chatId);
            ChatDTO chat = rabbitTemplate.convertSendAndReceiveAsType("", messagesQueueName,
                    command,
                    new ParameterizedTypeReference<ChatDTO>() {
                    });
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping(params = {"chatId"})
    public ResponseEntity<ChatDTO> changeAcceptStatus(@RequestParam UUID chatId) {
        try {
            ChangeAcceptStatusRequest command = new ChangeAcceptStatusRequest(chatId);
            ChatDTO chat = rabbitTemplate.convertSendAndReceiveAsType("", messagesQueueName,
                    command,
                    new ParameterizedTypeReference<ChatDTO>() {
                    });
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}