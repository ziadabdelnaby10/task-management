package com.ziad.task.controller;

import com.ziad.task.config.JwtTokenService;
import com.ziad.task.model.dto.UserDto;
import com.ziad.task.model.request.AddUserRequest;
import com.ziad.task.model.request.LoginRequest;
import com.ziad.task.model.response.AuthenticationResponse;
import com.ziad.task.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth")
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid @NotNull LoginRequest loginRequest) {
        log.info("The User in login endpoint : {}", loginRequest != null ? loginRequest.toString() : "NONE");

        StringBuilder jwt = new StringBuilder();
        var authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.email(), loginRequest.password());
        var authenticationResponse = authenticationManager.authenticate(authentication);

        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            log.debug("The User Details : {}", authenticationResponse.getName() + " " + authenticationResponse.getAuthorities());
            jwt.insert(0, jwtTokenService.generateToken(authenticationResponse.getName() , authenticationResponse.getAuthorities()));
        }

        return new ResponseEntity<>(new AuthenticationResponse(new Date(), "Bearer", jwt.toString(), 123L), HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid AddUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(request));
    }
}
