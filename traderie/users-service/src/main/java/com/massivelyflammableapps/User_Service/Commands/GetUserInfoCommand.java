package com.massivelyflammableapps.User_Service.Commands;

import com.Traderie_User.User_Service.UserService.UserService;
import com.massivelyflammableapps.User_Service.UserService.UserService;
import lombok.*;

@Data
@AllArgsConstructor
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
