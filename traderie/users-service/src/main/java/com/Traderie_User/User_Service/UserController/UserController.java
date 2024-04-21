package com.Traderie_User.User_Service.UserController;


import com.Traderie_User.User_Service.Responses.LoginResponse;
import com.Traderie_User.User_Service.Responses.ResponseMessage;
import com.Traderie_User.User_Service.User.User;
import com.Traderie_User.User_Service.UserService.UserService;
import com.Traderie_User.User_Service.dto.UserRegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        User user = null;
        return ResponseEntity.ok(user);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestParam String token
    ) {

        return ResponseEntity.ok("logout successfully");
    }
    //send request for offers and listings to delete the offers/listings
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





