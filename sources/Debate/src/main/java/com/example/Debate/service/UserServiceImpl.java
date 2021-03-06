package com.example.Debate.service;

import com.example.Debate.common.exception.ResourceNotFoundException;
import com.example.Debate.dto.request.UserDto;
import com.example.Debate.model.User;
import com.example.Debate.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id).get();
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login).orElseThrow(() ->
                new ResourceNotFoundException("User" + login));
        var mapped = modelMapper.map(user,UserDto.class);
        mapped.setPassword("");
        return mapped;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        var saved = userRepository.save(user);
        return modelMapper.map(saved,UserDto.class);
    }

    @Override
    public UserDto changeUserPassword(UserDto userDto) {
        User user = userRepository.findUserByLogin(userDto.getLogin()).orElseThrow(() ->
                new ResourceNotFoundException("User" + userDto.getLogin()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        var saved = userRepository.save(user);
        return modelMapper.map(saved,UserDto.class);
    }
}
