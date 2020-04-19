package com.example.Debate.service;

import com.example.Debate.controller.SearchController;
import com.example.Debate.dto.response.FullDebateResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SearchServiceImpl implements SearchService {

    @Override
    public List<FullDebateResponseDto> getDebatesDebatesContainingName(String reqName)
    {
        return null;
    }

    @Override
    public List<FullDebateResponseDto> getDebatesDebatesWithName(List<String> reqTags)
    {
        return null;
    }
}
