package com.massivelyflammableapps.messages.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Data;

@Data
@Table
public class Message {

    private UUID messageId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID senderId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID receiverId;

    @Column
    private String messageText;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    private LocalDateTime createdAt;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private UUID chatId;

    public Message(UUID senderId, UUID receiverId, String messageText, UUID chatId) {
        this.messageId = UUID.randomUUID();
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.createdAt = LocalDateTime.now();
        this.chatId = chatId;
    }
}