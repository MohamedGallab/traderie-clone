package com.massivelyflammableapps.shared.dto.users;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID; // Import UUID

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class UserDto implements UserDetails
{
    @Id
    private UUID user_id; // Use UUID for user_id

    private String username;


    private String password;

    private String email;

    private Date date_of_birth;

    private String timezone;

    @Builder.Default
    private String status = "ONLINE"; // Default value is ONLINE

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
}