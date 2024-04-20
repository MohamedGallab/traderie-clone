package com.Traderie_User.User_Service.dto;

import com.Traderie_User.User_Service.Validators.StrongPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"user\"")

public class UserRegister
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @NotNull
    @NotEmpty
    private String user_name;

    @NotNull
    @NotEmpty
    @StrongPassword
    private String password;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private Date date_of_birth;

    private LocalDateTime created_at = LocalDateTime.now();


}
