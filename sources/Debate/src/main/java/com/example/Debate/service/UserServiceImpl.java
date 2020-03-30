package com.example.Debate.service;

import com.example.Debate.dto.UserDto;
import com.example.Debate.model.User;
import com.example.Debate.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto getUserById(String id) {
        User user = userRepository.findById(id).get();
        return modelMapper.map(user,UserDto.class);
    }
}