package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.User_Service.UserService.UserService;
import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class GetUserInfoCommand extends AbstractCommand{
    @NonNull
    private UserService userService;

    @NonNull
    private String username;

    @Override
    public Object execute() {
        return userService.getUserInfo(username);
    }
}
