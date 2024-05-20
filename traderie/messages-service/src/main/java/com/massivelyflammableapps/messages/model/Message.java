package com.massivelyflammableapps.messages.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.massivelyflammableapps.shared.dto.messages.MessageDTO;

import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
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

    public Message(MessageDTO messageDTO) {
        this.messageId = messageDTO.getMessageId();
        this.senderId = messageDTO.getSenderId();
        this.receiverId = messageDTO.getReceiverId();
        this.messageText = messageDTO.getMessageText();
        this.createdAt = messageDTO.getCreatedAt();
        this.chatId = messageDTO.getChatId();
    }

    public MessageDTO toDTO() {
        return new MessageDTO(messageId, senderId, receiverId, messageText, createdAt, chatId);
    }
}