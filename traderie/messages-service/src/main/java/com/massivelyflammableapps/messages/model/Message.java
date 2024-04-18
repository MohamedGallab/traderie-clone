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
}