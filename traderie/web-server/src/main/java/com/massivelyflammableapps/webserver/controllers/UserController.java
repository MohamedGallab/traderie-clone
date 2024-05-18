package com.massivelyflammableapps.webserver.controllers;

import com.massivelyflammableapps.shared.dto.users.*;
import com.massivelyflammableapps.webserver.configuration.JwtUtils;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor

public class UserController {
    @Value("${jwt.secret}")
    private String base64SecretBytes;

    @Value("${users-service.queue.name}")
    private String queueName;

    @Autowired
    private UserDetailsService userDetailsService;

    private final JwtUtils jwtUtils;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/getUser")
    public ResponseEntity<UserDto> getDTO(
            @RequestParam String username
    ) {
        try {
        System.out.println(username);
        GetUserDTORequest command = new GetUserDTORequest(username);
        UserDto response =rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                new ParameterizedTypeReference<UserDto>() {
                });
        return ResponseEntity.ok(response);
    } catch (Exception e)
    {
        e.printStackTrace();
        return ResponseEntity.status(500).build();
    }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<String> registerUser(
           @RequestBody UserRegisterDto userRegister) {
        RegisterRequest command = new RegisterRequest(userRegister);
        Object user = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                new ParameterizedTypeReference<Object>() {
                });
        List<String> response = List.of(user.toString().split("="));
        if(Objects.equals(response.get(3).substring(0,3), "400")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.get(1));}
        else
            return ResponseEntity.status(HttpStatus.CREATED).body("Sign Up Successful");
    }

    @GetMapping
    public ResponseEntity<Object> getUserInfo(
            @RequestHeader("UUID") String uuid
        ) {
        try {
            GetUserInfoRequest command = new GetUserInfoRequest(uuid);
            Object response =rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                    new ParameterizedTypeReference<Object>() {
                    });
            return ResponseEntity.ok(response);
        } catch (Exception e)
        {
        e.printStackTrace();
        return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/login")
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        LoginRequest command = new LoginRequest(loginRequest);
        Object user = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                new ParameterizedTypeReference<Object>() {
                });

        final UserDto userDto = (UserDto) userDetailsService.loadUserByUsername(loginRequest.getUsername());
        List<String> response = List.of(user.toString().split("="));
        if(Objects.equals(response.get(2).substring(0,3), "404")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.get(1));}
        else if( Objects.equals(response.get(2).substring(0,3), "401")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.get(1));}
        else {
            return ResponseEntity.status(HttpStatus.OK).body(response.get(1));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader("UUID") String uuid
    ) {
        LogoutRequest command = new LogoutRequest(uuid);
        Object response = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                new ParameterizedTypeReference<Object>() {
                });
        if (response==null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok("Logout successful");
        }
    }
    //send request for offers and listings to delete the offers/listings
    @DeleteMapping
    public ResponseEntity<String> deleteUser(
            @RequestHeader("UUID") String uuid
    ) {
        DeleteUserRequest command = new DeleteUserRequest(uuid);
        Object response = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                new ParameterizedTypeReference<Object>() {
                });
        List<String> res = List.of(response.toString().split("="));
        if(Objects.equals(res.get(3).substring(0,3), "404")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res.get(1));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(res.get(1));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getUserStatus(
            @RequestHeader("UUID") String uuid
    ) {
        GetUserStatusRequest command = new GetUserStatusRequest(uuid);
        Object response =rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                new ParameterizedTypeReference<Object>() {
                });
        if (response==null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(response.toString());
        }
    }
//    private Optional<User> getUserDetailsFromToken(String token) {
//        try {
//            Claims claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token).getBody();
//            String username = claims.getSubject();
//            // Assuming you have a method to load UserDetails from your user repository
//            Optional<User> userDetails = userRepository.findByUsername(username);
//            return userDetails;
//        } catch (Exception e) {
//            // Error parsing token or user not found, return null
//            return null;
//        }
//    }
}





