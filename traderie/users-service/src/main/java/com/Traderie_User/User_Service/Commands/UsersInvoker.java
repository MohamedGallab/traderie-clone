package com.Traderie_User.User_Service.Commands;

import com.Traderie_User.User_Service.UserService.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersInvoker {
    @Autowired
    private UserService userService;

    @RabbitListener(queues = {"hello"})
    public <T> T excute(AbstractCommand command) {
        command.setUserService(userService);
        return command.execute();
    }
}
