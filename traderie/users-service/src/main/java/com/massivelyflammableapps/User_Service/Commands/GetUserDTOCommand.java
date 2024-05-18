package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.dto.users.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class GetUserDTOCommand extends AbstractCommand{
    @NonNull
    private UserService userService;

    @NonNull
    private String username;

    @Override
    public UserDto execute() {
        return userService.getDTO(username);
    }
}
