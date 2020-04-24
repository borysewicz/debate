package com.example.Debate.service;

import com.example.Debate.dto.request.AddOrUpdateArgumentDto;
import com.example.Debate.dto.response.ArgumentResponse;

import java.util.List;
import java.util.Optional;

public interface ArgumentService {
    List<ArgumentResponse> getArgumentsForDebate(String debateId, int limit, int page, Optional<String> userId);
    ArgumentResponse addArgument(AddOrUpdateArgumentDto argumentDto, String userId);
    ArgumentResponse getArgumentById(String id, Optional<String> userId);
}
