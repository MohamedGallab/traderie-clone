package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import lombok.Data;

@Data
public abstract class AbstractCommand<T> {
    private UserService userService;
    public abstract T execute();
}