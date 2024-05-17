package com.massivelyflammableapps.messages.commands;

import com.massivelyflammableapps.messages.service.MessagesService;

import lombok.Data;

@Data
public abstract class MessagesAbstractCommand<T> {
    private MessagesService messagesService;

    public abstract T execute();
}
