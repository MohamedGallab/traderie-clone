package com.Traderie_User.User_Service.UserController;


import com.Traderie_User.User_Service.User.User;
import com.Traderie_User.User_Service.UserService.UserService;
import com.Traderie_User.User_Service.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor

public class UserController {
    private UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)

    public void registerUser(
            @Valid @RequestBody int user
    ) {
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<Map<String, String>> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Login Successfully");
        responseBody.put("token", "token");

        return ResponseEntity.ok(responseBody);
    }


    @GetMapping
    public ResponseEntity<User> getUserInfo(
            @RequestParam String token
    ) {

        User user = null;
        return ResponseEntity.ok(user);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestParam String token
    ) {

        return ResponseEntity.ok("logout successfully");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(
            @RequestParam String token
    ) {

        return ResponseEntity.ok("deleted successfully");
    }

    @GetMapping("/status")
    public ResponseEntity<String> getUserStatus(
            @RequestParam String token
    ) {

        return ResponseEntity.ok("deleted successfully");
    }

}


