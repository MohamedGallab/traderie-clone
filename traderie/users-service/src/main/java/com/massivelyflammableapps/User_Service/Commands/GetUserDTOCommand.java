package com.massivelyflammableapps.User_Service.Commands;

import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.dto.users.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@NoArgsConstructor
public class GetUserDTOCommand extends AbstractCommand<UserDto>{
    @NonNull
    private UserService userService;

    @NonNull
    private String username;

    @Override
    public UserDto execute() {
        return userService.getDTO(username);
    }
}
