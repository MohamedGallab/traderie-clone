package com.massivelyflammableapps.User_Service.UserController;


import com.massivelyflammableapps.User_Service.Commands.AbstractCommand;
import com.massivelyflammableapps.User_Service.Commands.LoginCommand;
import com.massivelyflammableapps.User_Service.Configuration.JwtUtils;
import com.massivelyflammableapps.User_Service.UserService.UserService;
import com.massivelyflammableapps.shared.dto.users.AuthenticationRequest;
import com.massivelyflammableapps.shared.dto.users.LoginRequestDto;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    @Value("${service.queue.name}")
    private String queueName;

    @Autowired
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    @PermitAll
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        AbstractCommand command = new LoginCommand(loginRequest);
        Object user = rabbitTemplate.convertSendAndReceiveAsType("", queueName, command,
                new ParameterizedTypeReference<Object>() {
                });
        List<String> response = List.of(user.toString().split("="));
        if(Objects.equals(response.get(2).substring(0,3), "404")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found, Try Sign Up");}
        else if( Objects.equals(response.get(2).substring(0,3), "401")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong password");}
        else {
            return ResponseEntity.status(HttpStatus.OK).body("Login Successfully");
        }
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





