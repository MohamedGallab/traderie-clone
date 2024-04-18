package com.massivelyflammableapps.messages.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.util.List;
import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.service.MessageService;
import com.massivelyflammableapps.messages.model.MessageRequest;
import java.util.UUID;

@RestController
@RequestMapping("/messages")
public class MessageController {

    // @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequest messageRequest) {
        messageService.sendMessage(messageRequest.getSenderId(), messageRequest.getReceiverId(),
                messageRequest.getChatId(), messageRequest.getContent());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chats")
    public ResponseEntity<List<Chat>> getChats(@RequestParam UUID userId, @RequestParam String status) {
        List<Chat> chats = messageService.getChats(userId, status);
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/chats/{chatId}")
    public ResponseEntity<Chat> getChat(@PathVariable UUID chatId) {
        Chat chat = messageService.getChat(chatId);
        if (chat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(chat);
    }
}