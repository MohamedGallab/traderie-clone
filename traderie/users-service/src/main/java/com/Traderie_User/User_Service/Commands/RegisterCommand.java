package com.Traderie_User.User_Service.Commands;

import com.Traderie_User.User_Service.UserService.UserService;
import com.Traderie_User.User_Service.dto.UserRegisterDto;
import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class RegisterCommand extends AbstractCommand{
    private UserService userService;

    @NonNull
    private UserRegisterDto userRegisterDto;

    @Override
    public Object execute() {
        return userService.registerUser(userRegisterDto);
    }
}
