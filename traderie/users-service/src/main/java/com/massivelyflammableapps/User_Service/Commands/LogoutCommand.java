package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class LogoutCommand extends AbstractCommand<Object>{

    @NonNull
    private UserService userService;

    @NonNull
    private String token;

    @Override
    public Object execute() {
        return userService.logout(token);
    }
}
