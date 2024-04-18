package com.massivelyflammableapps.messages.service;

import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.Message;

import com.massivelyflammableapps.messages.repository.ChatRepository;
import com.massivelyflammableapps.messages.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// MessageServiceImpl.java
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public void sendMessage(UUID senderId, String receiverId, UUID chatId, String content) {
        // Create and save message
        Message message = new Message();
        message.setSenderId(senderId);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);

        // Update chat if exists
        Optional<Chat> optionalChat = chatRepository.findByChatId(chatId);
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            chat.getMessages().add(message);
            chatRepository.save(chat);
        } else {
            // Create new chat if doesn't exist
            Chat chat = new Chat();
            chat.setChatId(chatId);
            chat.setMessages(Collections.singletonList(message));
            chat.setArchived(false);
            chatRepository.save(chat);
        }

    }

    @Override
    public List<Chat> getChats(UUID userId, String status) {
        return chatRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public Chat getChat(UUID chatId) {
        return chatRepository.findByChatId(chatId).orElse(null);
    }
}