package com.massivelyflammableapps.shared.dto.messages.chats;

import java.util.UUID;

import lombok.Data;

@Data
public class ChatRequest {
    UUID initiatorId;
    UUID receiverId;
    boolean isAccepted;
    String messageContent;
}
