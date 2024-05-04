package com.massivelyflammableapps.messages.commands.messages;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.messages.service.ChatService;
import com.massivelyflammableapps.messages.service.MessageService;

@Service
public class MessagesInvoker {
    @Autowired
    private MessageService messageService;

    @RabbitListener(queues = { "hello" })
    public <T> T execute(MessagesAbstractCommand message) {
        message.setMessageService(messageService);
        return message.execute();
    }
}
