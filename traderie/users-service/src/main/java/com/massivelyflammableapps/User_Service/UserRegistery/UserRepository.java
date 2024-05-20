package com.massivelyflammableapps.User_Service.UserRegistery;

import com.massivelyflammableapps.User_Service.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    void delete(User user);
}
