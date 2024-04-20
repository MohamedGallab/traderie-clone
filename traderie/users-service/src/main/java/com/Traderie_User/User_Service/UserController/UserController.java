package com.Traderie_User.User_Service.UserController;


import com.Traderie_User.User_Service.Responses.LoginResponse;
import com.Traderie_User.User_Service.Responses.ResponseMessage;
import com.Traderie_User.User_Service.User.User;
import com.Traderie_User.User_Service.UserService.UserService;
import com.Traderie_User.User_Service.dto.LoginRequestDto;
import com.Traderie_User.User_Service.dto.UserRegisterDto;
import jakarta.annotation.security.PermitAll;
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

    public ResponseEntity<ResponseMessage> registerUser(
            @Valid @RequestBody UserRegisterDto user
    ) {

        ResponseMessage response = new ResponseMessage();
        response.setMessage("user registered successfully");

        return ResponseEntity.ok(response);


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





