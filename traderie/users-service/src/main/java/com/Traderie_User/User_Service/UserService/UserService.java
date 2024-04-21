package com.Traderie_User.User_Service.UserService;

import com.Traderie_User.User_Service.Responses.ResponseMessage;
import com.Traderie_User.User_Service.User.User;
import com.Traderie_User.User_Service.UserRegistery.UserRepository;
import com.Traderie_User.User_Service.Validators.ObjectsValidator;
import com.Traderie_User.User_Service.Validators.StrongPasswordValidator;
import com.Traderie_User.User_Service.dto.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private final ObjectsValidator<UserRegisterDto> uservalidator;
    private final UserRepository userRepository;

    public Object registerUser(UserRegisterDto user) {

        var violations  = uservalidator.validate(user);
        if(!violations.isEmpty()){
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setMessage(String.join("|",violations));
            responseMessage.setStatus("400");
            return responseMessage;
        }
        // Create a new UserRegister instance
        boolean email = userRepository.findByEmail(user.getEmail()).isPresent();
        boolean userName = userRepository.findByUsername(user.getUsername()).isPresent();
        if(email){
           throw new IllegalStateException("email already exists");

        }
        if(userName){
            throw new IllegalStateException("email already exists");
        }
        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .date_of_birth(user.getDate_of_birth())
                .build();

        // Set the created_at field
        newUser.setCreated_at(LocalDateTime.now());


        // Save the new user to the database
        userRepository.save(newUser);
        return new ResponseMessage("User created successfully","201");
    }
}
