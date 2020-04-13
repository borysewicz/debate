package com.example.Debate.controller;

import com.example.Debate.dto.response.UserDto;
import com.example.Debate.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/userLogin")
public class UserLoginController {

    private UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/logIn")
    public String login(@RequestBody UserDto userDto){
        long currentTime = System.currentTimeMillis();
        if(userService.loadUserByUsername(userDto.getLogin()).getUsername().equals(userDto.getLogin())) {
            return Jwts.builder()
                    .setSubject(userDto.getLogin())
                    .claim("roles", String.valueOf(userDto.getRole()))
                    .setIssuedAt(new Date(currentTime))
                    .setExpiration(new Date(currentTime + 20000))
                    .signWith(SignatureAlgorithm.HS512, userDto.getPassword().getBytes())
                    .compact();
        }
        return "NOT EXIST";
    }
}
