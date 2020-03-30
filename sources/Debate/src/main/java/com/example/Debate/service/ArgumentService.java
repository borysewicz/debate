package com.example.Debate.service;

import com.example.Debate.dto.ArgumentDto;

public interface ArgumentService {
    public ArgumentDto getArgumentById(String id);
    public boolean addArgument(ArgumentDto argumentDto);
}
