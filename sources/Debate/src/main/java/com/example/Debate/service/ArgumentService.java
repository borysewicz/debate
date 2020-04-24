package com.example.Debate.service;

import com.example.Debate.dto.request.AddOrUpdateArgumentDto;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.ArgumentResponse;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ArgumentService {
    List<ArgumentResponse> getArgumentsForDebate(String debateId, int limit, int page, Optional<String> userId);
    ArgumentResponse addArgument(AddOrUpdateArgumentDto argumentDto, String userId);
    ArgumentResponse getArgumentById(String id, Optional<String> userId);
    void deleteArgument(String id, Principal principal);
    void updateArgument(String id, AddOrUpdateArgumentDto argumentDto, Principal principal);
    ActivityHistoryResponse getArgumentHistory(String id);
}
