package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.dto.users.UserRegisterDto;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
public class RegisterCommand extends AbstractCommand<Object>{
    @NonNull
    private UserService userService;

    @NonNull
    private UserRegisterDto userRegisterDto;

    @Override
    public Object execute() {
        return userService.registerUser(userRegisterDto);
    }
}
