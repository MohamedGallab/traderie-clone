package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.dto.users.LoginRequestDto;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class LoginCommand extends AbstractCommand<Object>{

    private UserService userService;
    @NonNull
    private LoginRequestDto loginRequestDto;

    @Override
    public Object execute() {
        return userService.login(loginRequestDto);
    }
}
