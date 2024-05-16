package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.dto.users.LoginRequestDto;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class LoginCommand extends AbstractCommand{

    private UserService userService;
    @NonNull
    private LoginRequestDto loginRequestDto;

    @Override
    public Object execute() {
        return userService.login(loginRequestDto);
    }
}
