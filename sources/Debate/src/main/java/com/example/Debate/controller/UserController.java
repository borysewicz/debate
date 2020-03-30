package com.example.Debate.controller;

import com.example.Debate.dto.UserDto;
import com.example.Debate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
