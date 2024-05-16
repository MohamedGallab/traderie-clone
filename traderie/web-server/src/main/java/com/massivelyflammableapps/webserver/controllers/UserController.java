package com.massivelyflammableapps.webserver.controllers;

import com.massivelyflammableapps.shared.dto.users.GetUserInfoNStatusNLogoutRequest;
import com.massivelyflammableapps.shared.dto.users.RegisterRequest;
import com.massivelyflammableapps.shared.dto.users.UserRegisterDto;
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
    private RabbitTemplate rabbitTemplate;

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
                @RequestHeader(HttpHeaders.AUTHORIZATION) String token
        ) {
            GetUserInfoNStatusNLogoutRequest command = new GetUserInfoNStatusNLogoutRequest("test3");
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
        GetUserInfoNStatusNLogoutRequest command = new GetUserInfoNStatusNLogoutRequest("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MyIsImV4cCI6MTcxNTk1OTM3NCwiaWF0IjoxNzE1ODcyOTc0LCJhdXRob3JpdGllcyI6W119.pHabTBXXXSnBFj-5r9CSaB7yvFIzWp1yoLjF7gRnA7Q");
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
        GetUserInfoNStatusNLogoutRequest command = new GetUserInfoNStatusNLogoutRequest("test3");
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
        GetUserInfoNStatusNLogoutRequest command = new GetUserInfoNStatusNLogoutRequest("test3");
        Object response =rabbitTemplate.convertSendAndReceive("", queueName, command);
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





