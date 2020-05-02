package com.example.Debate.service;

import com.example.Debate.common.exception.ResourceNotFoundException;
import com.example.Debate.common.exception.UnauthorizedAccessException;
import com.example.Debate.dto.request.AddOrUpdateCommentDto;
import com.example.Debate.dto.request.RatingRequest;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.CommentResponse;
import com.example.Debate.dto.response.RatingResponse;
import com.example.Debate.model.Comment;
import com.example.Debate.model.Debate;
import com.example.Debate.model.enums.Vote;
import com.example.Debate.repository.ArgumentRepository;
import com.example.Debate.repository.CommentRepository;
import com.example.Debate.repository.DebateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private ArgumentRepository argumentRepository;
    private DebateRepository debateRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              ArgumentRepository argumentRepository,
                              DebateRepository debateRepository,
                              ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.argumentRepository = argumentRepository;
        this.debateRepository = debateRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentResponse getCommentById(String id, Optional<String> userLogin) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment", id));
        return modelMapper.map(comment, CommentResponse.class)
                .withUserVote(comment.getUserVote(userLogin));
    }

    @Override
    public CommentResponse addCommentOnArgument(AddOrUpdateCommentDto commentDto, Principal principal) {
        var comment = modelMapper.map(commentDto, Comment.class);
        comment.setAuthor(principal.getName());
        comment.ratePost(principal.getName(), Vote.POSITIVE);
        var saved = commentRepository.save(comment);
        String parentId = comment.getParentActivityId();
        var argument = argumentRepository.findById(parentId).orElseThrow(() ->
                new ResourceNotFoundException("Argument", parentId));
        argument.getComments().add(saved.get_id());
        argumentRepository.save(argument);
        return modelMapper.map(saved, CommentResponse.class).withUserVote(Vote.POSITIVE);
    }

    @Override
    public CommentResponse addCommentOnDebate(AddOrUpdateCommentDto commentDto, Principal principal) {
        var comment = modelMapper.map(commentDto, Comment.class);
        comment.setAuthor(principal.getName());
        comment.ratePost(principal.getName(), Vote.POSITIVE);
        var saved = commentRepository.save(comment);
        String parentId = comment.getParentActivityId();
        var debate = debateRepository.findById(parentId).orElseThrow(() ->
                new ResourceNotFoundException("Argument", parentId));
        debate.getComments().add(saved.get_id());
        debateRepository.save(debate);
        return modelMapper.map(saved, CommentResponse.class).withUserVote(Vote.POSITIVE);
    }

    @Override
    public List<CommentResponse> getCommentsForActivity(String activityId, Optional<String> userLogin) {
        return commentRepository.findByParentActivityId(activityId).stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class)
                        .withUserVote(comment.getUserVote(userLogin)))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(String id, Principal principal) {
        var comment = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment", id));
        if (comment.isAuthorized(principal)) {
            commentRepository.deleteById(id);
        } else throw new UnauthorizedAccessException();
    }

    @Override
    public void updateComment(AddOrUpdateCommentDto commentDto, String commentId, Principal principal) {
        var comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", commentId));
        if (comment.isAuthorized(principal)) {
            comment.saveEdit();
            comment.setContent(commentDto.getContent());
            commentRepository.save(comment);
        } else throw new UnauthorizedAccessException();
    }

    @Override
    public ActivityHistoryResponse getCommentHistory(String id) {
        var comment = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment", id));
        return new ActivityHistoryResponse(comment.getEditHistory());
    }

    @Override
    public RatingResponse rateComment(String commentId, RatingRequest ratingRequest, Principal principal) {
        var argument = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", commentId));
        String name = principal.getName();
        argument.ratePost(name, ratingRequest.getVote());
        var saved = commentRepository.save(argument);
        return modelMapper.map(saved, RatingResponse.class);
    }
}
