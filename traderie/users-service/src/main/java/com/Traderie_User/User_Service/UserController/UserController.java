package com.Traderie_User.User_Service.UserController;


import com.Traderie_User.User_Service.Responses.ResponseMessage;
import com.Traderie_User.User_Service.User.User;
import com.Traderie_User.User_Service.User.UserStatus;
import com.Traderie_User.User_Service.UserService.UserService;
import com.Traderie_User.User_Service.dto.UserRegisterDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor

public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<ResponseMessage> registerUser(
           @RequestBody UserRegisterDto userRegister) {

        ResponseMessage responseMessage = (ResponseMessage) userService.registerUser(userRegister);
        if(responseMessage.getStatus().equals("400"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }


        @GetMapping
    public ResponseEntity<User> getUserInfo(
            @RequestParam String token
    ) {
        Claims claims = Jwts.parser().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        User user = userService.getUserInfo(username);
        if(user != null)
            return null;
        else
            return ResponseEntity.ok(user);
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(
            @RequestParam String token
    ) {
        ResponseMessage responseMessage = (ResponseMessage) userService.logout(token);
        if(responseMessage.getStatus().equals("400"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }
    //send request for offers and listings to delete the offers/listings
    @DeleteMapping
    public ResponseEntity<ResponseMessage> deleteUser(
            @RequestParam String token
    ) {
        Claims claims = Jwts.parser().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        ResponseMessage responseMessage = (ResponseMessage) userService.deleteUser(username);
        if(responseMessage.getStatus().equals("400"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @GetMapping("/status")
    public UserStatus getUserStatus(
            @RequestParam String token
    ) {
        Claims claims = Jwts.parser().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        return userService.getUserStatus(username);
    }

}





