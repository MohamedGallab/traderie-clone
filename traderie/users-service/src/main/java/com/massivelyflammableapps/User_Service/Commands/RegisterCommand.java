package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.dto.users.UserRegisterDto;
import lombok.*;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class RegisterCommand extends AbstractCommand{
    @NonNull
    private UserService userService;

    @NonNull
    private UserRegisterDto userRegisterDto;

    @Override
    public Object execute() {
        return userService.registerUser(userRegisterDto);
    }
}
