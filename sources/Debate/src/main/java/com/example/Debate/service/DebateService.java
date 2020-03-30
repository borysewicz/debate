package com.example.Debate.service;

import com.example.Debate.dto.DebateDto;

public interface DebateService {
    public DebateDto getDebateById(String id);
    public boolean addDebate(DebateDto debateDto);
}
