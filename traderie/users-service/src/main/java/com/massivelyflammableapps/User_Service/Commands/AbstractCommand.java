package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.User_Service.UserService.UserService;
import lombok.Data;

@Data
public abstract class AbstractCommand {
    private UserService userService;
    public abstract <T> T execute();
}