package com.Traderie_User.User_Service.UserController;


import com.Traderie_User.User_Service.Commands.*;
import com.Traderie_User.User_Service.Configuration.JwtUtils;
import com.Traderie_User.User_Service.User.User;
import com.Traderie_User.User_Service.UserRegistery.UserRepository;
import com.Traderie_User.User_Service.dto.UserRegisterDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Value("${service.queue.name}")
    private String queueName;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<String> registerUser(
           @RequestBody UserRegisterDto userRegister) {
        AbstractCommand command = new RegisterCommand(userRegister);
        Object user = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                new ParameterizedTypeReference<Object>() {
                });
        System.out.println(user);
        List<String> response = List.of(user.toString().split("="));
        if(Objects.equals(response.get(3).substring(0,3), "400")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.get(1));}
        else
            return ResponseEntity.status(HttpStatus.CREATED).body("Sign Up Successful");
    }


        @GetMapping
    public ResponseEntity<Object> getUserInfo(
                @RequestHeader(HttpHeaders.AUTHORIZATION) String token
        ) {
            String tokenwWithoutHeader= token.substring(7);
            Optional<User> userDetails = getUserDetailsFromToken(tokenwWithoutHeader);
            User user = userDetails.get();
            if (!jwtUtils.isTokenValid(tokenwWithoutHeader, user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        Claims claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(tokenwWithoutHeader).getBody();
        String username = claims.getSubject();
        System.out.println(username+ " "+ claims);
            AbstractCommand command = new GetUserInfoCommand(username);
            Object response =rabbitTemplate.convertSendAndReceive("", queueName, command);

            if (response==null) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(response);
            }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        String tokenwWithoutHeader= token.substring(7);
        AbstractCommand command = new LogoutCommand(tokenwWithoutHeader);
        Object response = rabbitTemplate.convertSendAndReceive("",queueName, command);
        if (response==null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok("Logout successful");
        }
    }
    //send request for offers and listings to delete the offers/listings
    @DeleteMapping
    public ResponseEntity<String> deleteUser(

            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        String tokenwWithoutHeader= token.substring(7);
        Optional<User> userDetails = getUserDetailsFromToken(tokenwWithoutHeader);
        User user = userDetails.get();
        if (!jwtUtils.isTokenValid(tokenwWithoutHeader, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Claims claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(tokenwWithoutHeader).getBody();
        String username = claims.getSubject();
        AbstractCommand command = new DeleteUserCommand(username);
        Object response = rabbitTemplate.convertSendAndReceive("", queueName, command);
        List<String> res = List.of(response.toString().split("="));
        if(Objects.equals(res.get(3).substring(0,3), "404")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res.get(1));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(res.get(1));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<String> getUserStatus(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token
    ) {
        String tokenwWithoutHeader= token.substring(7);
        Optional<User> userDetails = getUserDetailsFromToken(tokenwWithoutHeader);
        User user = userDetails.get();
        if (!jwtUtils.isTokenValid(tokenwWithoutHeader, user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Claims claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(tokenwWithoutHeader).getBody();
        String username = claims.getSubject();
        AbstractCommand command = new GetUserStatus(username);
        Object response =rabbitTemplate.convertSendAndReceive("", queueName, command);
        if (response==null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(response.toString());
        }
    }
    private Optional<User> getUserDetailsFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(base64SecretBytes).parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            // Assuming you have a method to load UserDetails from your user repository
            Optional<User> userDetails = userRepository.findByUsername(username);
            return userDetails;
        } catch (Exception e) {
            // Error parsing token or user not found, return null
            return null;
        }
    }
}





