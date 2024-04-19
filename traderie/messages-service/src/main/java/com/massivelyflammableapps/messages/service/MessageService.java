package com.massivelyflammableapps.messages.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.repository.MessageRepository;


public class MessageService implements IMessageService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> getChatMessages(UUID chatId) {
        List<Message> chatMessages = messageRepository.findByChatId(chatId);
        return chatMessages;
    }

    public ResponseEntity<Message> postMessage(Message request) {
        try {
            Message newMessage = new Message(request.getSenderId(), request.getReceiverId(), request.getMessageText(),
                    request.getChatId());

            Message response = messageRepository.save(newMessage);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            // Return an error response entity
            return ResponseEntity.status(500).build();
        }
    }
}
