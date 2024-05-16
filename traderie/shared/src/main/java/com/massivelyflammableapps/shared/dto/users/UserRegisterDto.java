package com.massivelyflammableapps.shared.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserRegisterDto
{

    @NotNull(message = "User name shouldn't be null")
    @NotEmpty(message = "User name shouldn't be empty")
    private String username;

    @NotNull(message = "Password shouldn't be null")
    @NotEmpty(message = "Password shouldn't be empty")
    @StrongPassword(message = "The password should be at least 8 characters," +
            " contains uppercase letter,lowercase letters, digits, special character")
    private String password;

    @NotNull(message = "Email shouldn't be null")
    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Incorrect format of email")
    private String email;

    @NotNull(message = "Password shouldn't be null")
    private Date date_of_birth;

    private LocalDateTime created_at = LocalDateTime.now();


}
