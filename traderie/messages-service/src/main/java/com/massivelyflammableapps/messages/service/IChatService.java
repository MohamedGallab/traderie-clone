package com.massivelyflammableapps.messages.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.massivelyflammableapps.messages.model.Chat;

public interface IChatService {
    public List<Chat> getChatMessages(UUID initiatorId, UUID receiverId);

    public List<Chat> getUserChats(UUID userId);

    public ResponseEntity<Chat> postChat(Chat request);

    public ResponseEntity<Chat> changeArchiveStatus(UUID chatId);

    public ResponseEntity<Chat> changeAcceptStatus(UUID chatId);
}
