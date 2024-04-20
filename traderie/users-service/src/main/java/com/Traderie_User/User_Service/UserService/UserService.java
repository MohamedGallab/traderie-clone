package com.Traderie_User.User_Service.UserService;

import com.Traderie_User.User_Service.UserRegistery.UserRepository;
import com.Traderie_User.User_Service.Validators.ObjectsValidator;
import com.Traderie_User.User_Service.dto.UserRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ObjectsValidator<UserRegister> validator;
    private final UserRepository userRepository;

    public Object registerUser(String userName, String password, String email, Date dateOfBirth) {

        // Create a new UserRegister instance
        UserRegister newUser = UserRegister.builder()
                .user_name(userName)
                .password(passwordEncoder.encode(password))
                .email(email)
                .date_of_birth(dateOfBirth)
                .build();

        // Set the created_at field
        newUser.setCreated_at(LocalDateTime.now());

        var violations  = validator.validate(newUser);
        if(!violations.isEmpty()){
            return String.join("|",violations);
        }
        // Save the new user to the database
        return userRepository.save(newUser);
    }
}
