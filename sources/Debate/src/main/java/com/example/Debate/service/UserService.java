package com.example.Debate.service;

import com.example.Debate.dto.response.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserDto getUserById(String id);
    public UserDto addUser(UserDto userDto);
}
