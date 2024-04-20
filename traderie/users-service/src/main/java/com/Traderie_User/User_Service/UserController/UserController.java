package com.Traderie_User.User_Service.UserController;


import com.Traderie_User.User_Service.User.User;
import com.Traderie_User.User_Service.UserService.UserService;
import com.Traderie_User.User_Service.dto.UserRegister;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor

public class UserController {
    private UserService userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)

    public UserRegister registerUser(@RequestBody UserRegister userRegister) {
        // You can also extract the fields from the request body and pass them individually to the service
        // For simplicity, this example assumes the entire UserRegister object is passed in the request body
        return userService.registerUser(userRegister.getUser_name(), userRegister.getPassword(), userRegister.getEmail(), userRegister.getDate_of_birth());
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





