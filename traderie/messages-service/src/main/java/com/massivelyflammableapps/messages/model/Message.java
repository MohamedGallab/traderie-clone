package com.massivelyflammableapps.messages.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("messages")
public class Message {
    @PrimaryKeyColumn(name = "message_id", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private UUID messageId;

    @Column("sender_id")
    private UUID senderId;

    @Column("receiver_id")
    private UUID receiverId;

    @Column("message_text")
    private String messageText;

    @PrimaryKeyColumn(name = "created_at", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private LocalDateTime createdAt;

    @PrimaryKeyColumn(name = "chat_id", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
    private UUID chatId;

    public Message(UUID messageId, UUID senderId, UUID receiverId, String messageText, UUID chatId) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.chatId = chatId;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public String getMessageText() {
        return messageText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UUID getChatId() {
        return chatId;
    }

}