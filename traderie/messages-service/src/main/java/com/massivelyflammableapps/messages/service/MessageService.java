package com.massivelyflammableapps.messages.service;

import com.massivelyflammableapps.messages.model.Chat;
import java.util.List;
import java.util.UUID;

public interface MessageService {
    void sendMessage(UUID senderId, String receiverId, UUID chatId, String content);

    List<Chat> getChats(UUID userId, String status);

    Chat getChat(UUID chatId);
}