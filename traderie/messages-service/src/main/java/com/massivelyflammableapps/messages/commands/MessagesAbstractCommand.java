package com.massivelyflammableapps.messages.commands;

import com.massivelyflammableapps.messages.service.MessagesService;

import lombok.Data;

@Data
public abstract class MessagesAbstractCommand {
    private MessagesService messagesService;

    public abstract <T> T execute();
}
