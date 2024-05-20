package com.massivelyflammableapps.User_Service.User;

import com.massivelyflammableapps.shared.dto.users.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID; // Import UUID

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "\"user\"")

public class User implements UserDetails
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
    private Date date_of_birth;

    private String timezone;

    @Builder.Default
    @Enumerated(EnumType.STRING) // Map enum as a String
    private UserStatus status = UserStatus.ONLINE; // Default value is ONLINE

    @Builder.Default
    private LocalDateTime created_at = LocalDateTime.now();
    private String biography;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public UserDto toDTO() {
        return new UserDto(user_id,username,password,email,date_of_birth,timezone,String.valueOf(status),created_at,biography);
    }
}