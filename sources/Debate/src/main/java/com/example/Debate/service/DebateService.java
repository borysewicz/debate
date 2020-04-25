package com.example.Debate.service;

import com.example.Debate.common.api.SortingType;
import com.example.Debate.dto.request.AddOrUpdateDebateDto;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.FullDebateResponseDto;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface DebateService {
    FullDebateResponseDto getDebateById(String id);

    List<FullDebateResponseDto> getDebates(SortingType sortingType, int limit, int skip);

    FullDebateResponseDto addDebate(AddOrUpdateDebateDto debateDto, MultipartFile debateCover, Principal principal);

    Binary getDebateCover(String id);

    void delete(String id, Principal principal);

    void update(String id, AddOrUpdateDebateDto debateDto, MultipartFile debateCover, Principal principal);

    ActivityHistoryResponse getDebateHistory(String id);

}
