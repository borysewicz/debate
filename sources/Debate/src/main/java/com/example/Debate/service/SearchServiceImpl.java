package com.example.Debate.service;

import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.model.Argument;
import com.example.Debate.model.Comment;
import com.example.Debate.model.Debate;
import com.example.Debate.model.enums.Vote;
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
        List<Debate> debates= debateRepository.findByTitleRegex(mongoRegex.toString()).stream()
                .collect(Collectors.toList());
        List<FullDebateResponseDto> resultingDtos = new ArrayList<>();
        for(var debate: debates)
            resultingDtos.add(castDebateToDtoAndFillOutStats(debate));
        return resultingDtos;
    }

    @Override
    public List<FullDebateResponseDto> getDebatesDebatesWithTags(List<String> reqTags)
    {
        Criteria criteria = Criteria.where("allTags").in(reqTags);
        Query query = new Query(criteria);
        List<Debate> matchingDebates = mongoTemplate.find(query,Debate.class).stream()
                .collect(Collectors.toList());
        Map<Debate, Integer> matchingTagsCountMap = new HashMap<>();
        Set<String> tagsSet = new HashSet<>();
        for(var currDebateDto: matchingDebates)
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
        matchingDebates.sort(Comparator.comparingInt(matchingTagsCountMap::get).reversed());
        List<FullDebateResponseDto> resultingDtos = new ArrayList<>();
        for(var debate: matchingDebates)
            resultingDtos.add(castDebateToDtoAndFillOutStats(debate));
        return resultingDtos;
    }

    private FullDebateResponseDto castDebateToDtoAndFillOutStats(Debate debate)
    {
        Set<String> argumentIds = debate.getArguments();
        Set<String> commentIds = debate.getComments();
        Criteria whereIdInArgumentIds = Criteria.where("_id").in(argumentIds);
        Query selectArguments = new Query(whereIdInArgumentIds);
        List<Argument> childArguments = mongoTemplate.find(selectArguments,Argument.class);
        Criteria whereIdInCommentIds = Criteria.where("_id").in(commentIds);
        Query selectComments= new Query(whereIdInCommentIds);
        List<Comment> connectedComments = mongoTemplate.find(selectComments,Comment.class);
        int argCount = argumentIds.size();
        int commCount = commentIds.size();
        int voteCount = 0;
        Set<String> participants = new HashSet<>();
        for(var post: childArguments)
        {
            Map<String, Vote> votes = post.getVoters();
            voteCount += votes.size();
            participants.addAll(votes.keySet());
        }
        for(var post: connectedComments)
        {
            Map<String, Vote> votes = post.getVoters();
            voteCount += votes.size();
            participants.addAll(votes.keySet());
        }
        return new FullDebateResponseDto(debate.get_id(),
                debate.getContent(),
                debate.getTitle(),
                debate.getMainTags(),
                debate.getAllTags(),
                debate.getCreationDate(),
                debate.getLastEditTime(),
                argCount,
                commCount,
                voteCount,
                participants.size(),
                debate.getAuthor());
    }

}