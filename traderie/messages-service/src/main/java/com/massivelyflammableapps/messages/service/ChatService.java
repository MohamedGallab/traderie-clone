package com.massivelyflammableapps.messages.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.repository.ChatRepository;

@Service
public class ChatService implements IChatService {
    @Autowired
    ChatRepository chatRepository;

    public List<Chat> getChatMessages(@RequestParam(required = true) UUID initiatorId,
            @RequestParam(required = true) UUID receiverId) {
        List<Chat> userChats = chatRepository.findByInitiatorIdAndReceiverId(initiatorId, receiverId);
        return userChats;
    }

    public List<Chat> getUserChats(@RequestParam(required = true) UUID userId) {
        List<Chat> chatsInitiator = chatRepository.findByInitiatorId(userId);
        List<Chat> chatsReceiver = chatRepository.findByReceiverId(userId);
        chatsInitiator.addAll(chatsReceiver);
        return chatsInitiator;
    }

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

    public ResponseEntity<Chat> changeArchiveStatus(@RequestParam(required = true) UUID chatId) {
        Chat chatData = chatRepository.findByChatId(chatId);

        if (chatData == null) {
            return ResponseEntity.notFound().build();
        }

        chatData.setArchived(!chatData.isArchived());
        chatRepository.save(chatData);
        return ResponseEntity.ok(chatData);
    }

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
