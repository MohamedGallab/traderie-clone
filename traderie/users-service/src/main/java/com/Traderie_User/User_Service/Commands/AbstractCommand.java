package com.Traderie_User.User_Service.Commands;

import com.Traderie_User.User_Service.UserService.UserService;
import lombok.Data;

@Data
public abstract class AbstractCommand {
    private UserService userService;
    public abstract <T> T execute();
}