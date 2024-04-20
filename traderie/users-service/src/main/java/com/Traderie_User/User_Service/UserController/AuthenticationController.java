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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor

public class AuthenticationController {
    private UserService userService;



    @PostMapping("/login")
    @PermitAll
    @ResponseStatus(HttpStatus.CREATED)

    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequestDto loginRequest
    ) {
        LoginResponse response = new LoginResponse();
        response.setMessage("Login Successful");
        response.setToken("token");

        return ResponseEntity.ok(response);
    }

}





