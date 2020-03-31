package com.example.Debate.service;

import com.example.Debate.dto.DebateDto;

import java.util.List;

public interface DebateService {
    public DebateDto getDebateById(String id);
    public List<DebateDto> getAllDebates();
    public boolean addDebate(DebateDto debateDto);
}
