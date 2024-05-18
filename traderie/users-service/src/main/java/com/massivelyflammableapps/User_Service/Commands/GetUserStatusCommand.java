package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class GetUserStatusCommand extends AbstractCommand{
    private UserService userService;

    @NonNull
    private String username;

    @Override
    public Object execute() {
        return userService.getUserStatus(username);
    }
}
