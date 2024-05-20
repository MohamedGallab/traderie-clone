package com.massivelyflammableapps.messages.commands;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import com.massivelyflammableapps.messages.commands.chats.ChangeAcceptStatusCommand;
import com.massivelyflammableapps.messages.commands.chats.ChangeArchiveStatusCommand;
import com.massivelyflammableapps.messages.commands.chats.GetUserChatsCommand;
import com.massivelyflammableapps.messages.commands.chats.PostChatCommand;
import com.massivelyflammableapps.messages.commands.chats.RequestChatCommand;
import com.massivelyflammableapps.messages.commands.messages.GetChatMessagesCommand;
import com.massivelyflammableapps.messages.commands.messages.PostMessageCommand;
import com.massivelyflammableapps.messages.service.MessagesService;
import com.massivelyflammableapps.shared.CommandHandler;
import com.massivelyflammableapps.shared.dto.AddCommandRequest;
import com.massivelyflammableapps.shared.dto.DeleteCommandRequest;
import com.massivelyflammableapps.shared.dto.ExecuteCommandRequest;
import com.massivelyflammableapps.shared.dto.UpdateCommandRequest;
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
    @Async
    @RabbitHandler
    public CompletableFuture<MessageDTO> postMessage(@Payload PostMessageRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PostMessageCommand command = new PostMessageCommand(messagesService, request.getMessage());
                return command.execute();
            } catch (Exception e) {
                return new MessageDTO();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<MessageDTO>> getChatMessages(@Payload GetChatMessagesRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetChatMessagesCommand command = new GetChatMessagesCommand(messagesService, request.getMessage());
                return command.execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    // chat commands
    @Async
    @RabbitHandler
    public CompletableFuture<ChatDTO> changeAcceptStatus(@Payload ChangeAcceptStatusRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ChangeAcceptStatusCommand command = new ChangeAcceptStatusCommand(messagesService, request.getChatId());
                return command.execute();
            } catch (Exception e) {
                return new ChatDTO();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<ChatDTO> changeArchiveStatus(@Payload ChangeArchiveStatusRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                ChangeArchiveStatusCommand command = new ChangeArchiveStatusCommand(messagesService,
                        request.getChatId());
                return command.execute();
            } catch (Exception e) {
                return new ChatDTO();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<List<ChatDTO>> getUserChats(@Payload GetUserChatsRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetUserChatsCommand command = new GetUserChatsCommand(messagesService, request.getUserId());
                return command.execute();
            } catch (Exception e) {
                return new ArrayList<>();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<ChatDTO> postChat(@Payload PostChatRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                PostChatCommand command = new PostChatCommand(messagesService, request.getChatRequest());
                return command.execute();
            } catch (Exception e) {
                return new ChatDTO();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<ChatDTO> requestChat(@Payload RequestChatRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                RequestChatCommand command = new RequestChatCommand(messagesService, request.getChatRequest());
                return command.execute();
            } catch (Exception e) {
                return new ChatDTO();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> addCommand(@Payload AddCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> deleteCommand(@Payload DeleteCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return commandHandler.deleteCommandFile(request.getCommandClass());
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Boolean> updateCommand(@Payload UpdateCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                boolean deleteResult = commandHandler.deleteCommandFile(request.getCommandClass());
                if (!deleteResult) {
                    return false;
                }
                return commandHandler.createCommandFile(request.getCommandClass(), request.getCommandCode());
            } catch (Exception e) {
                return false;
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> executeCommand(@Payload ExecuteCommandRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Object result = commandHandler.runIt(request.getCommandClass(), request.getParamsObj());
                if (result == null) {
                    return "void";
                }
                return result;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
