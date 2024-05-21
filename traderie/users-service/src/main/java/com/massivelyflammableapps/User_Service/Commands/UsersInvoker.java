package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.CommandHandler;
import com.massivelyflammableapps.shared.dto.admin.AddCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.DeleteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.ExecuteCommandRequest;
import com.massivelyflammableapps.shared.dto.admin.UpdateCommandRequest;
import com.massivelyflammableapps.shared.dto.users.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

@Service
@RabbitListener(queues = { "${service.queue.name}", "${service.queue.name}" + "_admin" })
public class UsersInvoker {

    @Autowired
    private UserService userService;

    private CommandHandler commandHandler = new CommandHandler();

    @Async
    @RabbitHandler
    public CompletableFuture<UserDto> getUserDTO(@Payload GetUserDTORequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetUserDTOCommand command = new GetUserDTOCommand(userService, request.getUsername());
                return command.execute();
            } catch (Exception e) {
                return new UserDto();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> register(@Payload RegisterRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                RegisterCommand command = new RegisterCommand(userService, request.getUser());
                return command.execute();
            } catch (Exception e) {
                return new Object();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> getUserInfo(@Payload GetUserInfoRequest userInfoRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetUserInfoCommand command = new GetUserInfoCommand(userService, userInfoRequest.getUserId());
                return command.execute();
            } catch (Exception e) {
                return new Object();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> getUserStatus(@Payload GetUserStatusRequest userInfoRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                GetUserStatusCommand command = new GetUserStatusCommand(userService, userInfoRequest.getUserId());
                return command.execute();
            } catch (Exception e) {
                return new Object();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> logout(@Payload LogoutRequest userInfoRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                LogoutCommand command = new LogoutCommand(userService, userInfoRequest.getUserId());
                return command.execute();
            } catch (Exception e) {
                return new Object();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> deleteUser(@Payload DeleteUserRequest userInfoRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                DeleteUserCommand command = new DeleteUserCommand(userService, userInfoRequest.getUserId());
                return command.execute();
            } catch (Exception e) {
                return new Object();
            }
        });
    }

    @Async
    @RabbitHandler
    public CompletableFuture<Object> login(@Payload LoginRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                LoginCommand command = new LoginCommand(userService, request.getUser());
                return command.execute();
            } catch (Exception e) {
                return new Object();
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
