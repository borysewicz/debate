package com.example.Debate.service;

import com.example.Debate.common.api.SortingType;
import com.example.Debate.common.exception.BadRequestException;
import com.example.Debate.common.exception.ResourceNotFoundException;
import com.example.Debate.common.exception.UnauthorizedAccessException;
import com.example.Debate.dto.request.AddOrUpdateDebateDto;

import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.ArgumentResponse;
import com.example.Debate.dto.response.CommentResponse;
import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.jwt.UserPrincipal;
import com.example.Debate.model.Argument;
import com.example.Debate.model.Comment;
import com.example.Debate.model.Debate;
import com.example.Debate.model.enums.Vote;
import com.example.Debate.repository.ArgumentRepository;
import com.example.Debate.repository.CommentRepository;
import com.example.Debate.repository.DebateRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DebateServiceImpl implements DebateService {
    private DebateRepository debateRepository;
    private ArgumentRepository argumentRepository;
    private CommentRepository commentRepository;
    private MongoTemplate mongoTemplate;
    private ModelMapper modelMapper;

    @Autowired
    public DebateServiceImpl(DebateRepository debateRepository,
                             ArgumentRepository argumentRepository,
                             CommentRepository commentRepository,
                             MongoTemplate mongoTemplate,
                             ModelMapper modelMapper) {
        this.debateRepository = debateRepository;
        this.argumentRepository =  argumentRepository;
        this.commentRepository = commentRepository;
        this.mongoTemplate = mongoTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public FullDebateResponseDto getDebateById(String id) {
        return modelMapper.map(
                debateRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Debate", id)),
                FullDebateResponseDto.class);
    }

    //TODO: Add other ways of sorting the debates, after comments and activities have been added to the platform
    @Override
    public List<FullDebateResponseDto> getDebates(SortingType sortingType, int limit, int page) {
        PageRequest request = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "creationDate"));
        List<Debate> debateList =  debateRepository.findAll(request).get().collect(Collectors.toList());
        List<FullDebateResponseDto> resultingDtos = new ArrayList<>();
        for(var debate: debateList)
            resultingDtos.add(castDebateToDtoAndFillOutStats(debate));
        return resultingDtos;
    }

    @Override
    public FullDebateResponseDto addDebate(AddOrUpdateDebateDto debateDto, MultipartFile debateCover, Principal principal) {
        Debate debate = modelMapper.map(debateDto, Debate.class);
        debate.setAuthor(principal.getName());
        if (debateCover != null) {
            try {
                debate.setImage(new Binary(BsonBinarySubType.BINARY, debateCover.getBytes()));
            } catch (IOException e) {
                throw new BadRequestException("Server encountered error while processing image, try again");
            }
        }
        var saved = debateRepository.save(debate);
        return modelMapper.map(saved, FullDebateResponseDto.class);
    }

    @Override
    public Binary getDebateCover(String id) {
        var debate = debateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Debate  with id %s not found", id)));
        var img = debate.getImage();
        if (img == null) {
            throw new ResourceNotFoundException(String.format("Debate with id %s has no cover image", id));
        }
        return img;
    }

    @Override
    public void delete(String id, Principal principal) {
        var debate = debateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Debate", id));
        Set<String> argumentIds = debate.getArguments();
        Set<String> commentIds = debate.getComments();
        Criteria whereIdInArgumentIds = Criteria.where("_id").in(argumentIds);
        Query selectArguments = new Query(whereIdInArgumentIds);
        List<Argument> childArguments = mongoTemplate.find(selectArguments,Argument.class);
        for(var argument: childArguments)
            commentIds.addAll(argument.getComments());

        if (debate.isAuthorized(principal)) {
            Query deleteComments = new Query(Criteria.where("_id").in(commentIds));
            mongoTemplate.remove(deleteComments,Comment.class);
            Query deleteArguments = new Query(Criteria.where("_id").in(argumentIds));
            mongoTemplate.remove(deleteArguments,Argument.class);
            debateRepository.deleteById(id);
        } else throw new UnauthorizedAccessException();
    }

    @Override
    public void update(String id, AddOrUpdateDebateDto debateDto, MultipartFile debateCover, Principal principal) {
        var debate = debateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Debate", id));
        if (debate.isAuthorized(principal)) {
            debate.saveEdit();
            debate.setTitle(debateDto.getTitle());
            debate.setContent(debateDto.getDescription());
            debate.setAllTags(debateDto.getAllTags());
            debate.setMainTags(debateDto.getMainTags());
            if (debateCover != null) {
                try {
                    debate.setImage(new Binary(BsonBinarySubType.BINARY, debateCover.getBytes()));
                } catch (IOException e) {
                    throw new BadRequestException("Server encountered error while processing image, try again");
                }
            }
            debateRepository.save(debate);
        } else throw new UnauthorizedAccessException();
    }

    @Override
    public ActivityHistoryResponse getDebateHistory(String id) {
        var debate = debateRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Debate", id));
        return new ActivityHistoryResponse(debate.getEditHistory());
    }

    @Override
    public List<CommentResponse> getCommentsForDebate(String debateId, Optional<String> userLogin) {
        Debate currDebate = debateRepository.findById(debateId).orElseThrow(() ->
                new ResourceNotFoundException("Debate", debateId));
        Set<String> childCommentsSet = currDebate.getComments();
        Criteria criteria = Criteria.where("_id").in(childCommentsSet);
        Query query = new Query(criteria);
        return mongoTemplate.find(query,Comment.class).stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class)
                        .withUserVote(comment.getUserVote(userLogin)))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArgumentResponse> getArgumentsForDebate(String debateId, Optional<String> userLogin) {
        Debate currDebate = debateRepository.findById(debateId).orElseThrow(() ->
                new ResourceNotFoundException("Debate", debateId));
        Set<String> childArgumentsSet = currDebate.getArguments();
        Criteria criteria = Criteria.where("_id").in(childArgumentsSet);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Argument.class).stream()
                .map(argument -> modelMapper.map(argument, ArgumentResponse.class)
                        .withUserVote(argument.getUserVote(userLogin)))
                .collect(Collectors.toList());
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
        Set<String> participants = new HashSet<String>();
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
                0,
                debate.getAuthor());
    }

}