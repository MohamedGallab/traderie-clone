package com.massivelyflammableapps.messages.model;

import java.util.UUID;

public class MessageRequest {
    private UUID senderId;
    private String receiverId;
    private UUID chatId;
    private String content;

    // Constructor
    public MessageRequest(UUID senderId, String receiverId, UUID chatId, String content) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.chatId = chatId;
        this.content = content;
    }

    // Getters and setters
    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "MessageRequest{" +
                "senderId='" + senderId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", chatId='" + chatId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}