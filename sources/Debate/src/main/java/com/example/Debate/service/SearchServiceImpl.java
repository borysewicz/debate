package com.example.Debate.service;

import com.example.Debate.common.api.SortingType;
import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.model.Debate;
import com.example.Debate.repository.DebateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SearchServiceImpl implements SearchService {

    private DebateRepository debateRepository;
    private ModelMapper modelMapper;

    public SearchServiceImpl(DebateRepository debateRepository, ModelMapper modelMapper) {
        this.debateRepository = debateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FullDebateResponseDto> getDebatesDebatesContainingName(String reqName)
    {
        StringBuilder mongoRegex = new StringBuilder("(?i)");//Mongo phrase for "Case insensitive on"
        mongoRegex.append(reqName);
        return debateRepository.findByTitleRegex(mongoRegex.toString()).stream()
                .map(mapEntity -> modelMapper.map(mapEntity, FullDebateResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FullDebateResponseDto> getDebatesDebatesWithTags(List<String> reqTags)
    {
        List<FullDebateResponseDto> result = new ArrayList<>();
        return result;
    }
}