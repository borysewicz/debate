package com.example.Debate.service;

import com.example.Debate.common.exception.ResourceNotFoundException;
import com.example.Debate.common.exception.UnauthorizedAccessException;
import com.example.Debate.dto.request.AddOrUpdateArgumentDto;
import com.example.Debate.dto.request.RatingRequest;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.ArgumentResponse;
import com.example.Debate.dto.response.CommentResponse;
import com.example.Debate.dto.response.RatingResponse;
import com.example.Debate.model.Argument;
import com.example.Debate.model.Comment;
import com.example.Debate.model.enums.Attitude;
import com.example.Debate.model.enums.Vote;
import com.example.Debate.repository.ArgumentRepository;
import com.example.Debate.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ArgumentServiceImpl implements ArgumentService {
    private ArgumentRepository argumentRepository;
    private MongoTemplate mongoTemplate;
    private ModelMapper modelMapper;

    public ArgumentServiceImpl(ArgumentRepository argumentRepository, MongoTemplate mongoTemplate, ModelMapper modelMapper) {
        this.argumentRepository = argumentRepository;
        this.mongoTemplate = mongoTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ArgumentResponse> getArgumentsForDebate(String debateId, int limit, int page, Optional<String> userLogin) {
        var pageRequest = PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "rating"));
        return Stream.concat(
                argumentRepository.getArgumentsByAttitudeAndDebateId(Attitude.POSITIVE, debateId, pageRequest).get(),
                argumentRepository.getArgumentsByAttitudeAndDebateId(Attitude.NEGATIVE, debateId, pageRequest).get()
        ).map(argumentEntity -> modelMapper.map(argumentEntity, ArgumentResponse.class)
                .withUserVote(argumentEntity.getUserVote(userLogin)))
                .collect(Collectors.toList());
    }

    @Override
    public ArgumentResponse addArgument(AddOrUpdateArgumentDto argumentDto, String userId) {
        var argument = new Argument(
                argumentDto.getTitle(), argumentDto.getAttitude(), argumentDto.getDebateId(),
                argumentDto.getContent(), userId
        );
        argument.ratePost(userId, Vote.POSITIVE);
        return modelMapper.map(argumentRepository.save(argument), ArgumentResponse.class)
                .withUserVote(Vote.POSITIVE);
    }

    @Override
    public ArgumentResponse getArgumentById(String id, Optional<String> userLogin) {
        return argumentRepository.findById(id).map(entity -> modelMapper.map(entity, ArgumentResponse.class)
                .withUserVote(entity.getUserVote(userLogin)))
                .orElseThrow(() -> new ResourceNotFoundException("Argument", id));
    }

    @Override
    public void deleteArgument(String id, Principal principal) {
        var argument = argumentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Argument", id));
        if (argument.isAuthorized(principal)) {
            argumentRepository.deleteById(id);
        } else throw new UnauthorizedAccessException();
    }

    @Override
    public void updateArgument(String id, AddOrUpdateArgumentDto argumentDto, Principal principal) {
        var argument = argumentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Argument", id));
        if (argument.isAuthorized(principal)) {
            argument.saveEdit();
            argument.setTitle(argumentDto.getTitle());
            argument.setContent(argumentDto.getContent());
            argumentRepository.save(argument);
        } else throw new UnauthorizedAccessException();
    }

    @Override
    public ActivityHistoryResponse getArgumentHistory(String id) {
        var debate = argumentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Argument", id));
        return new ActivityHistoryResponse(debate.getEditHistory());
    }

    @Override
    public RatingResponse rateArgument(RatingRequest rating, Principal principal, String argumentId) {
        var argument = argumentRepository.findById(argumentId).orElseThrow(() ->
                new ResourceNotFoundException("Argument", argumentId));
        String name = principal.getName();
        argument.ratePost(name, rating.getVote());
        var saved = argumentRepository.save(argument);
        return modelMapper.map(saved, RatingResponse.class);
    }

    @Override
    public List<CommentResponse> getCommentsForArgument(String argumentId, Optional<String> userLogin) {
        Argument currArgument = argumentRepository.findById(argumentId).orElseThrow(() ->
                new ResourceNotFoundException("Debate", argumentId));
        Set<String> childCommentsSet = currArgument.getComments();
        Criteria criteria = Criteria.where("_id").in(childCommentsSet);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Comment.class).stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class)
                        .withUserVote(comment.getUserVote(userLogin)))
                .collect(Collectors.toList());
    }
}
