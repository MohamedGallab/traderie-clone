package com.massivelyflammableapps.messages.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Table("chats")
public class Chat {

    @PrimaryKey("chat_id")
    private UUID chatId;

    @Column("initiator_id")
    private UUID initiatorId;

    @Column("receiver_id")
    private UUID receiverId;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("is_accepted")
    private boolean isAccepted;

    @Column("is_archived")
    private boolean isArchived;

    public Chat(UUID initiatorId, UUID receiverId, boolean isAccepted) {
        this.chatId = UUID.randomUUID();
        this.initiatorId = initiatorId;
        this.receiverId = receiverId;
        this.createdAt = LocalDateTime.now();
        this.isAccepted = isAccepted;
        this.isArchived = false;
    }
}