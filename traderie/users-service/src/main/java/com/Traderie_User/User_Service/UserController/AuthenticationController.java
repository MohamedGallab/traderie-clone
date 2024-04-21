package com.Traderie_User.User_Service.UserController;


import com.Traderie_User.User_Service.Configuration.JwtUtils;
import com.Traderie_User.User_Service.Responses.LoginResponse;
import com.Traderie_User.User_Service.Responses.ResponseMessage;
import com.Traderie_User.User_Service.User.User;
import com.Traderie_User.User_Service.UserService.UserService;
import com.Traderie_User.User_Service.dto.AuthenticationRequest;
import com.Traderie_User.User_Service.dto.LoginRequestDto;
import com.Traderie_User.User_Service.dto.UserRegisterDto;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

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


    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if (userDetails != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));
        }
        return ResponseEntity.status(400).body("Some error has occurred");
    }
}





