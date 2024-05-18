package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
public class GetUserInfoCommand extends AbstractCommand<Object>{
    @NonNull
    private UserService userService;

    @NonNull
    private String username;

    @Override
    public Object execute() {
        return userService.getUserInfo(username);
    }
}
