package com.massivelyflammableapps.User_Service.Commands;

import com.Traderie_User.User_Service.UserService.UserService;
import com.massivelyflammableapps.User_Service.UserService.UserService;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class GetUserStatus extends AbstractCommand{
    private UserService userService;

    @NonNull
    private String username;

    @Override
    public Object execute() {
        return userService.getUserStatus(username);
    }
}
