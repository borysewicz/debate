package com.example.Debate.controller;

import com.example.Debate.dto.request.LoginRequestDto;
import com.example.Debate.dto.response.LoginResponse;
import com.example.Debate.jwt.TokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/userLogin")
public class UserLoginController {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;

    public UserLoginController(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/logIn")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        String token = tokenProvider.generateToken(authentication);
        LoginResponse loginResponse = new LoginResponse(token, authentication.getAuthorities().toString());
        return ResponseEntity.ok(loginResponse);
    }
}
