package com.massivelyflammableapps.shared.dto.messages;

import java.time.LocalDateTime;
import java.util.UUID;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private UUID messageId;
    private UUID senderId;
    private UUID receiverId;
    private String messageText;
    private LocalDateTime createdAt;
    private UUID chatId;
}
