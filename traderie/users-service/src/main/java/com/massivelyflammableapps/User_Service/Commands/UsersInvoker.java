package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.dto.users.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = {"${service.queue.name}"})
public class UsersInvoker {

    @Autowired
    private UserService userService;

    @RabbitHandler
    public UserDto getUserDTO (@Payload GetUserDTORequest request){
        GetUserDTOCommand command = new GetUserDTOCommand(userService,request.getUsername());
        return command.execute();
    }

    @RabbitHandler
    public Object register(@Payload RegisterRequest request) {
        RegisterCommand command = new RegisterCommand(userService, request.getUser());
        return command.execute();
    }

    @RabbitHandler
    public Object getUserInfo(@Payload GetUserInfoRequest userInfoRequest) {
        GetUserInfoCommand command = new GetUserInfoCommand(userService, userInfoRequest.getUserId());
        return command.execute();
    }

    @RabbitHandler
    public Object getUserStatus(@Payload GetUserStatusRequest userInfoRequest) {
        GetUserStatusCommand command = new GetUserStatusCommand(userService, userInfoRequest.getUserId());
        return command.execute();
    }

    @RabbitHandler
    public Object logout(@Payload LogoutRequest userInfoRequest) {
        LogoutCommand command = new LogoutCommand(userService, userInfoRequest.getUserId());
        return command.execute();
    }

    @RabbitHandler
    public Object deleteUser(@Payload DeleteUserRequest userInfoRequest) {
        DeleteUserCommand command = new DeleteUserCommand(userService, userInfoRequest.getUserId());
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
