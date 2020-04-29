package com.example.Debate.service;

import com.example.Debate.dto.response.FullDebateResponseDto;

import java.util.List;

public interface SearchService {
    List<FullDebateResponseDto> getDebatesDebatesContainingName(String reqName);
    List<FullDebateResponseDto> getDebatesDebatesWithTags(List<String> reqTags);
}
