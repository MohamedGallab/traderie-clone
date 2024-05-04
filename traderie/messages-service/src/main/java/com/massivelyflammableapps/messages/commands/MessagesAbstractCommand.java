package com.massivelyflammableapps.messages.commands;

import com.massivelyflammableapps.messages.service.MessageService;

import lombok.Data;

@Data
public abstract class MessagesAbstractCommand {
    private MessageService messageService;

    public abstract <T> T execute();
}
