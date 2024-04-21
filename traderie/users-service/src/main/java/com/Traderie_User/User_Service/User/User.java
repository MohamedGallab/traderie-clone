package com.Traderie_User.User_Service.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID; // Import UUID

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID user_id; // Use UUID for user_id

    @NotNull
    private String username;

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

    @Enumerated(EnumType.STRING) // Map enum as a String
    private UserStatus status = UserStatus.ONLINE; // Default value is ONLINE

    private LocalDateTime created_at = LocalDateTime.now();
    private String biography;

}
