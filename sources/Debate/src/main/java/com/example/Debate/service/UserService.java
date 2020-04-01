package com.example.Debate.service;

import com.example.Debate.dto.UserDto;

public interface UserService {
    public UserDto getUserById(String id);
    public boolean addUser(UserDto userDto);
}
