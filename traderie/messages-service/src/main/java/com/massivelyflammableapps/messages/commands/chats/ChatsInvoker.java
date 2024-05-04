package com.massivelyflammableapps.messages.commands.chats;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.messages.service.ChatService;

@Service
public class ChatsInvoker {
    @Autowired
    private ChatService chatService;

    @RabbitListener(queues = { "hello" })
    public <T> T execute(ChatsAbstractCommand message) {
        message.setChatService(chatService);
        return message.execute();
    }
}
