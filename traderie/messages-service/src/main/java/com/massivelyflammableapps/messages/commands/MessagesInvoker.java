package com.massivelyflammableapps.messages.commands;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.messages.commands.chats.ChangeAcceptStatusCommand;
import com.massivelyflammableapps.messages.commands.chats.ChangeArchiveStatusCommand;
import com.massivelyflammableapps.messages.commands.chats.GetUserChatsCommand;
import com.massivelyflammableapps.messages.commands.chats.PostChatCommand;
import com.massivelyflammableapps.messages.commands.chats.RequestChatCommand;
import com.massivelyflammableapps.messages.commands.messages.GetChatMessagesCommand;
import com.massivelyflammableapps.messages.commands.messages.PostMessageCommand;
import com.massivelyflammableapps.messages.service.MessagesService;
import com.massivelyflammableapps.shared.CommandHandler;
import com.massivelyflammableapps.shared.dto.admin.AddCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.DeleteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.ExecuteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.UpdateCommandRequest;
import com.massivelyflammableapps.shared.dto.messages.*;
import com.massivelyflammableapps.shared.dto.messages.chats.*;
import com.massivelyflammableapps.shared.dto.messages.messages.*;

@Service
@RabbitListener(queues = { "${service.queue.name}" })
public class MessagesInvoker {
    @Autowired
    private MessagesService messagesService;

    private CommandHandler commandHandler = new CommandHandler();

    // messages commands
    @RabbitHandler
    public MessageDTO postMessage(@Payload PostMessageRequest request) {
        PostMessageCommand command = new PostMessageCommand(messagesService, request.getMessage());
        return command.execute();
    }

    @RabbitHandler
    public List<MessageDTO> getChatMessages(@Payload GetChatMessagesRequest request) {
        GetChatMessagesCommand command = new GetChatMessagesCommand(messagesService, request.getMessage());
        return command.execute();
    }

    // chat commands
    @RabbitHandler
    public ChatDTO changeAcceptStatus(@Payload ChangeAcceptStatusRequest request) {
        ChangeAcceptStatusCommand command = new ChangeAcceptStatusCommand(messagesService, request.getChatId());
        return command.execute();
    }

    @RabbitHandler
    public ChatDTO changeArchiveStatus(@Payload ChangeArchiveStatusRequest request) {
        ChangeArchiveStatusCommand command = new ChangeArchiveStatusCommand(messagesService, request.getChatId());
        return command.execute();
    }

    @RabbitHandler
    public List<ChatDTO> getUserChats(@Payload GetUserChatsRequest request) {
        GetUserChatsCommand command = new GetUserChatsCommand(messagesService, request.getUserId());
        return command.execute();
    }

    @RabbitHandler
    public ChatDTO postChat(@Payload PostChatRequest request) {
        PostChatCommand command = new PostChatCommand(messagesService, request.getChatRequest());
        return command.execute();
    }

    @RabbitHandler
    public ChatDTO requestChat(@Payload RequestChatRequest request) {
        RequestChatCommand command = new RequestChatCommand(messagesService, request.getChatRequest());
        return command.execute();
    }

    // reflection commands

    @RabbitHandler
    public Boolean addCommand(@Payload AddCommandRequest request) {
        return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
    }

    @RabbitHandler
    public Boolean deleteCommand(@Payload DeleteCommandRequest request) {
        return commandHandler.deleteCommandFile(request.getCommandClass());
    }

    @RabbitHandler
    public Boolean updateCommand(@Payload UpdateCommandRequest request) {
        boolean deleteResult = commandHandler.deleteCommandFile(request.getCommandClass());
        if (!deleteResult) {
            return false;
        }
        return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
    }

    @RabbitHandler
    public Object executeCommand(@Payload ExecuteCommandRequest request) {
        var result = commandHandler.runIt(request.getCommandClass(), request.getParamsObj());
        if (result == null) {
            return "void";
        }
        return result;
    }

}
