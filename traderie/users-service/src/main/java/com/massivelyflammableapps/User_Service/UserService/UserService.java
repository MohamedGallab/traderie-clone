package com.massivelyflammableapps.User_Service.UserService;

import com.massivelyflammableapps.User_Service.Configuration.JwtUtils;
import com.massivelyflammableapps.User_Service.Responses.ResponseMessage;
import com.massivelyflammableapps.User_Service.User.User;
import com.massivelyflammableapps.User_Service.User.UserStatus;
import com.massivelyflammableapps.User_Service.UserRegistery.UserRepository;
import com.massivelyflammableapps.User_Service.Validators.ObjectsValidator;
import com.massivelyflammableapps.User_Service.UserRegistery.UserRepository;
import com.massivelyflammableapps.shared.dto.users.LoginRequestDto;
import com.massivelyflammableapps.shared.dto.users.UserRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
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
           return new ResponseMessage("email already exists=", "400");

        }
        if(userName){
            return new ResponseMessage("username already exists=", "400");
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
        return new ResponseMessage("User created successfully=","201");
    }


    public Object login(LoginRequestDto loginRequestDto) {
        if(loginRequestDto!= null){
        Optional<User> user = userRepository.findByUsername(loginRequestDto.getUsername());
        if(user.isEmpty()){
            return new ResponseMessage("User not found, Try Sign Up","404");
        }
        System.out.println(!passwordEncoder.matches(loginRequestDto.getPassword(), user.get().getPassword())+"ffffff");
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.get().getPassword())){
            return new ResponseMessage("Wrong password","401");
        }
        System.out.println("leeh");
        return new ResponseMessage("Login successfully","200");
    }
        return new ResponseMessage("User not found, Try Sign Up","404");
    }

    public Object logout(String token) {
        jwtUtils.invalidateToken(token);
        return new ResponseMessage("Logout successful","200");
    }

    public  Object deleteUser(String username){
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isEmpty()){
            return new ResponseMessage("User not found, Try Sign Up=","404");
        }
        User user = userOptional.get();
        userRepository.delete(user);
        return new ResponseMessage("User deleted successfully=","200");
    }

    public UserStatus getUserStatus(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(User::getStatus).orElse(null);
    }

    public User getUserInfo(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}
