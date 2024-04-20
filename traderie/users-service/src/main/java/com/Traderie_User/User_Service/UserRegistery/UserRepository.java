package com.Traderie_User.User_Service.UserRegistery;

import com.Traderie_User.User_Service.dto.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserRegister, Integer> {
    UserRegister findByEmail(String email);
}
