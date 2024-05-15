package com.Traderie_User.User_Service.Commands;

import com.Traderie_User.User_Service.UserService.UserService;
import com.Traderie_User.User_Service.dto.LoginRequestDto;
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
