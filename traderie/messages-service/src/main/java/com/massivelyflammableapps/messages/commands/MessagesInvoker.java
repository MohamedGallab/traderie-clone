package com.massivelyflammableapps.messages.commands;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.messages.service.MessagesService;

@Service
public class MessagesInvoker {
    @Autowired
    private MessagesService messagesService;

    @RabbitListener(queues = { "hello" })
    public <T> T execute(MessagesAbstractCommand message) {
        message.setMessagesService(messagesService);
        return message.execute();
    }
}
