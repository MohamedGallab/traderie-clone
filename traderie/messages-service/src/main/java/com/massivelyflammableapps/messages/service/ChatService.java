package com.massivelyflammableapps.messages.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.ChatByInitiator;
import com.massivelyflammableapps.messages.model.ChatByReceiver;
import com.massivelyflammableapps.messages.repository.ChatByInitiatorRepository;
import com.massivelyflammableapps.messages.repository.ChatByReceiverRepository;
import com.massivelyflammableapps.messages.repository.ChatRepository;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;

    @Autowired
    ChatByInitiatorRepository chatByInitiatorRepository;

    @Autowired
    ChatByReceiverRepository chatByReceiverRepository;

    // public List<Chat> getChatMessages(@RequestParam(required = true) UUID
    // initiatorId,
    // @RequestParam(required = true) UUID receiverId) {
    // List<Chat> userChats =
    // chatRepository.findByInitiatorIdAndReceiverId(initiatorId, receiverId);
    // return userChats;
    // }

    public List<Chat> getUserChats(UUID userId) {
        List<Chat> chatsInitiator = new ArrayList<>();
        List<ChatByInitiator> chatByInitiators = chatByInitiatorRepository.findByInitiatorId(userId);
        chatByInitiators.forEach(chatByInitiator -> chatsInitiator.add(chatByInitiator.getChat()));

        List<Chat> chatsReceiver = new ArrayList<>();
        List<ChatByReceiver> chatByReceivers = chatByReceiverRepository.findByReceiverId(userId);
        chatByReceivers.forEach(chatByReceiver -> chatsReceiver.add(chatByReceiver.getChat()));

        List<Chat> userChats = new ArrayList<>(chatsInitiator);
        userChats.addAll(chatsReceiver);

        return userChats;
    }

    public ResponseEntity<Chat> postChat(Chat request) {
        try {
            Chat newChat = new Chat(
                    request.getInitiatorId(),
                    request.getReceiverId(),
                    request.isAccepted());

            ChatByInitiator newChatByInitiator = new ChatByInitiator(
                    newChat.getChatId(),
                    request.getInitiatorId(),
                    request.getReceiverId(),
                    newChat.getCreatedAt(),
                    request.isAccepted(),
                    request.isArchived());

            ChatByReceiver newChatByReceiver = new ChatByReceiver(
                    newChat.getChatId(),
                    request.getInitiatorId(),
                    request.getReceiverId(),
                    newChat.getCreatedAt(),
                    request.isAccepted(),
                    request.isArchived());

            Chat response = chatRepository.save(newChat);
            chatRepository.save(newChat);
            chatByInitiatorRepository.save(newChatByInitiator);
            chatByReceiverRepository.save(newChatByReceiver);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    public ResponseEntity<Chat> changeArchiveStatus(UUID chatId) {
        Chat chatData = chatRepository.findByChatId(chatId);
        ChatByInitiator chatByInitiatorData = chatByInitiatorRepository
                .findByInitiatorIdAndChatId(chatData.getInitiatorId(), chatId);
        ChatByReceiver chatByReceiverData = chatByReceiverRepository.findByReceiverIdAndChatId(chatData.getReceiverId(),
                chatId);

        chatData.setArchived(!chatData.isArchived());
        chatByInitiatorData.setArchived(chatData.isArchived());
        chatByReceiverData.setArchived(chatData.isArchived());

        chatRepository.save(chatData);
        chatByInitiatorRepository.save(chatByInitiatorData);
        chatByReceiverRepository.save(chatByReceiverData);

        return ResponseEntity.ok(chatData);
    }

    public ResponseEntity<Chat> changeAcceptStatus(UUID chatId) {

        Chat chatData = chatRepository.findByChatId(chatId);
        ChatByInitiator chatByInitiatorData = chatByInitiatorRepository
                .findByInitiatorIdAndChatId(chatData.getInitiatorId(), chatId);
        ChatByReceiver chatByReceiverData = chatByReceiverRepository.findByReceiverIdAndChatId(chatData.getReceiverId(),
                chatId);

        chatData.setAccepted(!chatData.isAccepted());
        chatByInitiatorData.setAccepted(chatData.isAccepted());
        chatByReceiverData.setAccepted(chatData.isAccepted());

        chatRepository.save(chatData);
        chatByInitiatorRepository.save(chatByInitiatorData);
        chatByReceiverRepository.save(chatByReceiverData);

        return ResponseEntity.ok(chatData);
    }
}
