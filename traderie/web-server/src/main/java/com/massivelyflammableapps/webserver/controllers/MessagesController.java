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

import lombok.extern.slf4j.Slf4j;

import com.massivelyflammableapps.shared.dto.messages.*;

@RestController
@RequestMapping("api/v1/messages")
@Slf4j
public class MessagesController {

    @Value("${messages-service.queue.name}")
    private String messagesQueueName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping()
    public ResponseEntity<List<MessageDTO>> getChatMessages(@RequestBody MessageDTO request) {
        try {
            GetChatMessagesRequest command = new GetChatMessagesRequest(request);
            List<MessageDTO> messages = rabbitTemplate.convertSendAndReceiveAsType("", messagesQueueName, command,
                    new ParameterizedTypeReference<List<MessageDTO>>() {
                    });
            log.info("getChatMessages executed successfully.");
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping()
    public ResponseEntity<MessageDTO> postMessage(@RequestBody MessageDTO request) {
        try {
            PostMessageRequest command = new PostMessageRequest(request);
            MessageDTO message = rabbitTemplate.convertSendAndReceiveAsType("", messagesQueueName, command,
                    new ParameterizedTypeReference<MessageDTO>() {
                    });
            log.info("postMessage executed successfully.");
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping(value = "/getUserChats", params = { "userId" })
    public ResponseEntity<List<ChatDTO>> getUserChats(@RequestParam UUID userId) {
        try {
            GetUserChatsRequest command = new GetUserChatsRequest(userId);
            List<ChatDTO> chats = rabbitTemplate.convertSendAndReceiveAsType("", messagesQueueName,
            command,
            new ParameterizedTypeReference<List<ChatDTO>>() {
            });
            log.info("getUserChats executed successfully.");
            return ResponseEntity.ok(chats);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping(value = "/postChat")
    public ResponseEntity<ChatDTO> postChat(@RequestBody ChatRequest request) {
        try {
            PostChatRequest command = new PostChatRequest(request);
            ChatDTO chat = rabbitTemplate.convertSendAndReceiveAsType("", messagesQueueName,
                    command,
                    new ParameterizedTypeReference<ChatDTO>() {
                    });
            log.info("postChat executed successfully.");
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/requestChat")
    public ResponseEntity<ChatDTO> requestChat(@RequestBody ChatRequest request) {
        try {
            RequestChatRequest command = new RequestChatRequest(request);
            ChatDTO chat = rabbitTemplate.convertSendAndReceiveAsType("",
                    messagesQueueName,
                    command,
                    new ParameterizedTypeReference<ChatDTO>() {
                    });
            log.info("requestChat executed successfully.");
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping(value = "/changeArchiveStatus", params = { "chatId" })
    public ResponseEntity<ChatDTO> changeArchiveStatus(@RequestParam UUID chatId) {
        try {
            ChangeArchiveStatusRequest command = new ChangeArchiveStatusRequest(chatId);
            ChatDTO chat = rabbitTemplate.convertSendAndReceiveAsType("",
                    messagesQueueName,
                    command,
                    new ParameterizedTypeReference<ChatDTO>() {
                    });
                log.info("changeArchiveStatus executed successfully.");
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping(value = "/changeAcceptStatus", params = { "chatId" })
    public ResponseEntity<ChatDTO> changeAcceptStatus(@RequestParam UUID chatId) {
        try {
            ChangeAcceptStatusRequest command = new ChangeAcceptStatusRequest(chatId);
            ChatDTO chat = rabbitTemplate.convertSendAndReceiveAsType("",
                    messagesQueueName,
                    command,
                    new ParameterizedTypeReference<ChatDTO>() {
                    });
            log.info("changeAcceptStatus executed successfully.");
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }
}