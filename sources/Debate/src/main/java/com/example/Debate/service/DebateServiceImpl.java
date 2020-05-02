package com.example.Debate.service;

import com.example.Debate.common.api.SortingType;
import com.example.Debate.common.exception.BadRequestException;
import com.example.Debate.common.exception.ResourceNotFoundException;
import com.example.Debate.common.exception.UnauthorizedAccessException;
import com.example.Debate.dto.request.AddOrUpdateDebateDto;

import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.CommentResponse;
import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.jwt.UserPrincipal;
import com.example.Debate.model.Comment;
import com.example.Debate.model.Debate;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DebateServiceImpl implements DebateService {
    private DebateRepository debateRepository;
    private ArgumentRepository argumentRepository;
    private MongoTemplate mongoTemplate;
    private ModelMapper modelMapper;

    @Autowired
    public DebateServiceImpl(DebateRepository debateRepository,
                             ArgumentRepository argumentRepository,
                             MongoTemplate mongoTemplate,
                             ModelMapper modelMapper) {
        this.debateRepository = debateRepository;
        this.argumentRepository =  argumentRepository;
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
        return debateRepository.findAll(request).get()
                .map(mapEntity -> modelMapper.map(mapEntity, FullDebateResponseDto.class))
                .collect(Collectors.toList());
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
        if (debate.isAuthorized(principal)) {
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
}