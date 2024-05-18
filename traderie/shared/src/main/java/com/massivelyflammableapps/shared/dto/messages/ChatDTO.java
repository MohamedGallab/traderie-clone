package com.massivelyflammableapps.shared.dto.messages;

import java.io.Serializable;
import java.util.UUID;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO implements Serializable {
    private UUID chatId;
    private UUID initiatorId;
    private UUID receiverId;
    private LocalDateTime createdAt;
    private boolean isAccepted;
    private boolean isArchived;
}
