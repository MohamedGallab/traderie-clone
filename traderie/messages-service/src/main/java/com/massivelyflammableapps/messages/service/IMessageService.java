package com.massivelyflammableapps.messages.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.massivelyflammableapps.messages.model.Message;

public interface IMessageService {
    public List<Message> getChatMessages(UUID chatId);

    public ResponseEntity<Message> postMessage(Message request);
}