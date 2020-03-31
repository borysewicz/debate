package com.example.Debate.service;

import com.example.Debate.dto.ArgumentDto;

import java.util.List;

public interface ArgumentService {
    public ArgumentDto getArgumentById(String id);
    public List<ArgumentDto> getAllArguments();
    public boolean addArgument(ArgumentDto argumentDto);
}
