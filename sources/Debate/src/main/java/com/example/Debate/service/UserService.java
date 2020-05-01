package com.example.Debate.service;

import com.example.Debate.dto.request.UserDto;

public interface UserService {
    UserDto getUserById(String id);
    UserDto getUserByLogin(String login);
    UserDto addUser(UserDto userDto);
    UserDto changeUserPassword(UserDto userDto);
}
