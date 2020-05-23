package com.example.Debate.controller;

import com.example.Debate.dto.request.UserDto;
import com.example.Debate.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") String id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping(value = "/login/{login}")
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable(value = "login")String login){
        return ResponseEntity.ok(userService.getUserByLogin(login));
    }

    @PostMapping("/add")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        var newUser = userService.addUser(userDto);
        return ResponseEntity.created(URI.create("/api/user/")).body(newUser);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<UserDto> changeUserPassword(@RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.changeUserPassword(userDto));
    }
}
