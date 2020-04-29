package com.example.Debate.service;

import com.example.Debate.dto.request.UserDto;

public interface UserService {
    public UserDto getUserById(String id);
    public UserDto getUserByLogin(String login);
    public UserDto addUser(UserDto userDto);
    public UserDto changeUserPassword(UserDto userDto);
}
