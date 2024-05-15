package com.Traderie_User.User_Service.Commands;

import com.Traderie_User.User_Service.UserService.UserService;
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
