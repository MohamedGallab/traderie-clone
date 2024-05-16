package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.dto.users.*;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RabbitListener(queues = {"${service.queue.name}"})
public class UsersInvoker {

    @Autowired
    private UserService userService;

    @RabbitHandler
    public Object register(@Payload RegisterRequest request) {
        RegisterCommand command = new RegisterCommand(userService, request.getUser());
        return command.execute();
    }

    @RabbitHandler
    public Object getUserInfo(@Payload GetUserInfoRequest userInfoRequest) {
        GetUserInfoCommand command = new GetUserInfoCommand(userService, userInfoRequest.getUsername());
        return command.execute();
    }

    @RabbitHandler
    public Object getUserStatus(@Payload GetUserStatusRequest userInfoRequest) {
        GetUserStatus command = new GetUserStatus(userService, userInfoRequest.getUsername());
        return command.execute();
    }

    @RabbitHandler
    public Object logout(@Payload LogoutRequest userInfoRequest) {
        LogoutCommand command = new LogoutCommand(userService, userInfoRequest.getUsername());
        return command.execute();
    }

    @RabbitHandler
    public Object deleteUser(@Payload DeleteUserRequest userInfoRequest) {
        DeleteUserCommand command = new DeleteUserCommand(userService, userInfoRequest.getUsername());
        return command.execute();
    }

    @RabbitHandler
    public Object login(@Payload LoginRequest request) {
        LoginCommand command = new LoginCommand(userService, request.getUser());
        return command.execute();
    }

//    @RabbitListener(queues = {"${service.queue.name}"})
//    public <T> T excute(AbstractCommand command) {
//        command.setUserService(userService);
//        return command.execute();
//    }
}
