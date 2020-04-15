package com.example.Debate.controller;

import com.example.Debate.dto.response.UserDto;
import com.example.Debate.service.UserPrincipalService;
import com.example.Debate.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    public UserLoginController(UserPrincipalService userPrincipalService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userPrincipalService = userPrincipalService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/logIn")
    public String login(@RequestBody UserDto userDto){
        long currentTime = System.currentTimeMillis();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getLogin(),userDto.getPassword()));
//        if(userPrincipalService.loadUserByUsername(userDto.getLogin()).getUsername().equals(userDto.getLogin())) {
//            return Jwts.builder()
//                    .setSubject(userDto.getLogin())
//                    .claim("roles", String.valueOf(userDto.getRole()))
//                    .setIssuedAt(new Date(currentTime))
//                    .setExpiration(new Date(currentTime + 200000))
//                    .signWith(SignatureAlgorithm.HS512, userDto.getPassword().getBytes())
//                    .compact();
//        }
//        return "NOT EXIST";

    }
}
