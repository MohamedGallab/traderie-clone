package com.Traderie_User.User_Service.User;

import jakarta.persistence.*;
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

public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @NotNull
    @NotEmpty
    private String user_name;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private Date date_of_birth;

    private String timezone;
    private UserStatus status = UserStatus.ONLINE;
    private LocalDateTime created_at = LocalDateTime.now();
    private String biography;

}
