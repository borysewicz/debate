package com.example.Debate.service;

import com.example.Debate.dto.response.ArgumentResponse;
import com.example.Debate.model.Argument;
import com.example.Debate.repository.ArgumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArgumentServiceImpl implements ArgumentService{
    private ArgumentRepository argumentRepository;
    private ModelMapper modelMapper;

    public ArgumentServiceImpl(ArgumentRepository argumentRepository, ModelMapper modelMapper) {
        this.argumentRepository = argumentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ArgumentResponse getArgumentById(String id) {
        Argument argument = argumentRepository.findById(id).get();
        ArgumentResponse argumentDto = modelMapper.map(argument, ArgumentResponse.class);
        return argumentDto;
    }

    @Override
    public List<ArgumentResponse> getAllArguments() {
        List<Argument> argumentList = argumentRepository.findAll();
        List<ArgumentResponse> argumentDtoList = new ArrayList<>();
        if(argumentList.size() != 0){
            for(Argument argument : argumentList){
                argumentDtoList.add(modelMapper.map(argument, ArgumentResponse.class));
            }
        }
        return argumentDtoList;
    }

    @Override
    public boolean addArgument(ArgumentResponse argumentDto) {
        Argument argument = modelMapper.map(argumentDto,Argument.class);
        argumentRepository.save(argument);
        return true;
    }
}
