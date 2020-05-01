package com.example.Debate.service;

import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.model.Debate;
import com.example.Debate.repository.DebateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class SearchServiceImpl implements SearchService {

    private DebateRepository debateRepository;
    private ModelMapper modelMapper;
    private MongoTemplate mongoTemplate;

    public SearchServiceImpl(DebateRepository debateRepository, ModelMapper modelMapper, MongoTemplate mongoTemplate) {
        this.debateRepository = debateRepository;
        this.modelMapper = modelMapper;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<FullDebateResponseDto> getDebatesDebatesContainingName(String reqName)
    {
        StringBuilder mongoRegex = new StringBuilder("(?i)");
        mongoRegex.append(reqName.strip());
        return debateRepository.findByTitleRegex(mongoRegex.toString()).stream()
                .map(mapEntity -> modelMapper.map(mapEntity, FullDebateResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FullDebateResponseDto> getDebatesDebatesWithTags(List<String> reqTags)
    {
        Criteria criteria = Criteria.where("allTags").in(reqTags);
        Query query = new Query(criteria);
        List<FullDebateResponseDto> matchingDebateDtos = mongoTemplate.find(query,Debate.class).stream()
                .map(mapEntity -> modelMapper.map(mapEntity, FullDebateResponseDto.class))
                .collect(Collectors.toList());
        Map<FullDebateResponseDto, Integer> matchingTagsCountMap = new HashMap<>();
        Set<String> tagsSet = new HashSet<>();
        for(var currDebateDto: matchingDebateDtos)
        {
            String[] currTags = currDebateDto.getAllTags();
            int currMatchingTagsCount = 0;
            for(int i=0;i<currTags.length;i++)
            {
                if(reqTags.contains(currTags[i]))
                    currMatchingTagsCount++;
            }
            matchingTagsCountMap.put(currDebateDto,currMatchingTagsCount);
        }
        matchingDebateDtos.sort(Comparator.comparingInt(matchingTagsCountMap::get).reversed());
        return matchingDebateDtos;
    }

}