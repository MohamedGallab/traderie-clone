package com.massivelyflammableapps.messages.commands;

import com.massivelyflammableapps.messages.service.ChatService;

import lombok.Data;

@Data
public abstract class ChatsAbstractCommand {
    private ChatService chatService;

    public abstract <T> T execute();
}
