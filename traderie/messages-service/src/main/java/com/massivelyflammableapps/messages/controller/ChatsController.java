package com.massivelyflammableapps.messages.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.messages.commands.ChangeAcceptStatusCommand;
import com.massivelyflammableapps.messages.commands.ChangeArchiveStatusCommand;
import com.massivelyflammableapps.messages.commands.ChatsAbstractCommand;
import com.massivelyflammableapps.messages.commands.GetUserChatsCommand;
import com.massivelyflammableapps.messages.commands.PostChatCommand;
import com.massivelyflammableapps.messages.commands.RequestChatCommand;
import com.massivelyflammableapps.messages.dto.ChatRequest;
import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.ChatByInitiatorAndReceiver;
import com.massivelyflammableapps.messages.service.ChatService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RestController
@RequestMapping("api/v1/chats")
public class ChatsController {

    @Autowired
    ChatService chatService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // @GetMapping
    // public List<Chat> getChatMessages(@RequestParam(required = true) UUID
    // initiatorId,
    // @RequestParam(required = true) UUID receiverId) {
    // return chatService.getChatMessages(initiatorId, receiverId);
    // }

    // @GetMapping("/getUserChats")
    // public List<Chat> getUserChats(@RequestParam UUID userId) {
    // return chatService.getUserChats(userId);
    // }

    @GetMapping("/getUserChats")
    public ResponseEntity<List<Chat>> getUserChats(@RequestParam UUID userId) {
        try {
            ChatsAbstractCommand command = new GetUserChatsCommand(userId);
            List<Chat> chats = rabbitTemplate.convertSendAndReceiveAsType("", "hello",
                    command,
                    new ParameterizedTypeReference<List<Chat>>() {
                    });
            return ResponseEntity.ok(chats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Chat> postChat(@RequestBody ChatRequest request) {
        try {
            ChatsAbstractCommand command = new PostChatCommand(request);
            Chat chat = rabbitTemplate.convertSendAndReceiveAsType("", "hello",
                    command,
                    new ParameterizedTypeReference<Chat>() {
                    });
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/requestChat")
    public ResponseEntity<ChatByInitiatorAndReceiver> requestChat(@RequestBody ChatRequest request) {
        try {
            ChatsAbstractCommand command = new RequestChatCommand(request);
            ChatByInitiatorAndReceiver chat = rabbitTemplate.convertSendAndReceiveAsType("", "hello",
                    command,
                    new ParameterizedTypeReference<ChatByInitiatorAndReceiver>() {
                    });
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/changeArchiveStatus")
    public ResponseEntity<Chat> changeArchiveStatus(@RequestParam UUID chatId) {
        try {
            ChatsAbstractCommand command = new ChangeArchiveStatusCommand(chatId);
            Chat chat = rabbitTemplate.convertSendAndReceiveAsType("", "hello",
                    command,
                    new ParameterizedTypeReference<Chat>() {
                    });
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/changeAcceptStatus")
    public ResponseEntity<Chat> changeAcceptStatus(@RequestParam UUID chatId) {
        try {
            ChatsAbstractCommand command = new ChangeAcceptStatusCommand(chatId);
            Chat chat = rabbitTemplate.convertSendAndReceiveAsType("", "hello",
                    command,
                    new ParameterizedTypeReference<Chat>() {
                    });
            return ResponseEntity.ok(chat);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}