package com.massivelyflammableapps.messages.controller;

import org.springframework.web.bind.annotation.RestController;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.messages.commands.chats.ChangeAcceptStatusCommand;
import com.massivelyflammableapps.messages.commands.chats.ChangeArchiveStatusCommand;
import com.massivelyflammableapps.messages.commands.chats.GetUserChatsCommand;
import com.massivelyflammableapps.messages.commands.chats.PostChatCommand;
import com.massivelyflammableapps.messages.commands.chats.RequestChatCommand;
import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.ChatByInitiatorAndReceiver;
import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.service.MessagesService;
import com.massivelyflammableapps.shared.dto.messages.chats.ChatRequest;

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
@RequestMapping("api/v1/messages")
public class MessagesController {

    @Autowired
    MessagesService messagesService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping
    public List<Message> getChatMessages(@RequestBody Message request) {
        return messagesService.getChatMessages(request);
    }

    @PostMapping
    public Message postMessage(@RequestBody Message request) {
        return messagesService.postMessage(request);
    }

    @GetMapping("/getUserChats")
    public ResponseEntity<List<Chat>> getUserChats(@RequestParam UUID userId) {
        try {
            MessagesAbstractCommand command = new GetUserChatsCommand(userId);
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

    @PostMapping("/postChat")
    public ResponseEntity<Chat> postChat(@RequestBody ChatRequest request) {
        try {
            MessagesAbstractCommand command = new PostChatCommand(request);
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
            MessagesAbstractCommand command = new RequestChatCommand(request);
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
            MessagesAbstractCommand command = new ChangeArchiveStatusCommand(chatId);
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
            MessagesAbstractCommand command = new ChangeAcceptStatusCommand(chatId);
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