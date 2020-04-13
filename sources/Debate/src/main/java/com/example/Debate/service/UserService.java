package com.example.Debate.service;

import com.example.Debate.dto.response.UserDto;

public interface UserService {
    public UserDto getUserById(String id);
    public UserDto addUser(UserDto userDto);
}
