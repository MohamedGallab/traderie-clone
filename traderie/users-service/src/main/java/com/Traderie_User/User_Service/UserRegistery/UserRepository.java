package com.Traderie_User.User_Service.UserRegistery;

import com.Traderie_User.User_Service.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
