package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.User_Service.UserService.UserService;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class DeleteUserCommand extends AbstractCommand{
    private UserService userService;

    @NonNull
    private String username;
    @Override
    public Object execute() {
        return userService.deleteUser(username);
    }
}
