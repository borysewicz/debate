package com.example.Debate.service;

import com.example.Debate.dto.DebateDto;

import java.util.List;

public interface DebateService {
    DebateDto getDebateById(String id);
    List<DebateDto> getAllDebates();
    boolean addDebate(DebateDto debateDto);
}
