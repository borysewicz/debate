package com.example.Debate.service;

import com.example.Debate.dto.response.ArgumentResponse;

import java.util.List;

public interface ArgumentService {
    public ArgumentResponse getArgumentById(String id);
    public List<ArgumentResponse> getAllArguments();
    public boolean addArgument(ArgumentResponse argumentDto);
}
