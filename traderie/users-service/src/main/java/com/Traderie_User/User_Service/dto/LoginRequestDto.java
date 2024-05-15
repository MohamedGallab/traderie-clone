package com.Traderie_User.User_Service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto
{
    @NotEmpty
    @NotNull
    private String username;

    @NotEmpty
    @NotNull
    private String password;

}
