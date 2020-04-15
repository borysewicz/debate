package com.example.Debate.controller;

import com.example.Debate.dto.response.LoginResponse;
import com.example.Debate.dto.response.UserDto;
import com.example.Debate.jwt.TokenProvider;
import com.example.Debate.service.UserPrincipalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/userLogin")
public class UserLoginController {

    private UserPrincipalService userPrincipalService;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    public UserLoginController(UserPrincipalService userPrincipalService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.userPrincipalService = userPrincipalService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/logIn")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDto userDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getLogin(),userDto.getPassword()));
        String token = tokenProvider.generateToken(authentication);
        LoginResponse loginResponse = new LoginResponse(token,authentication.getAuthorities().toString());
        return ResponseEntity.ok(loginResponse);
    }
}
