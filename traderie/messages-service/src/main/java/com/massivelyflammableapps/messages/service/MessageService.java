package com.massivelyflammableapps.messages.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    public List<Message> getChatMessages(Message request) {
        List<Message> chatMessages = messageRepository.findByChatIdAndSenderIdAndReceiverId(request.getChatId(),
                request.getSenderId(),
                request.getReceiverId());
        return chatMessages;
    }

    public Message postMessage(Message request) {
        Message newMessage = new Message(request.getSenderId(), request.getReceiverId(), request.getMessageText(),
                request.getChatId());

        Message response = messageRepository.save(newMessage);
        return response;
    }
}
