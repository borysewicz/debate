package com.example.Debate.service;

import com.example.Debate.common.exception.BadRequestException;
import com.example.Debate.common.exception.ResourceNotFoundException;
import com.example.Debate.common.exception.UnauthorizedAccessException;
import com.example.Debate.dto.request.AddOrUpdateCommentDto;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.CommentResponse;
import com.example.Debate.model.Comment;
import com.example.Debate.model.Vote;
import com.example.Debate.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentResponse getCommentById(String id, Optional<String> userLogin) {
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment with id: " + id + " not found"));
        return modelMapper.map(comment, CommentResponse.class)
                .withUserVote(comment.getUserVote(userLogin));
    }

    @Override
    public CommentResponse addComment(AddOrUpdateCommentDto commentDto, Principal principal) {
        var comment = modelMapper.map(commentDto, Comment.class);
        comment.setAuthor(principal.getName());
        comment.ratePost(principal.getName(), Vote.POSITIVE);
        var saved = commentRepository.save(comment);
        return modelMapper.map(saved, CommentResponse.class).withUserVote(Vote.POSITIVE);
    }

    @Override
    public List<CommentResponse> getCommentsForActivity(String activityId, Optional<String> userLogin)
    {
        return commentRepository.findByParentActivityId(activityId).stream()
                .map(comment -> modelMapper.map(comment, CommentResponse.class)
                .withUserVote(comment.getUserVote(userLogin)))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(String id, Principal principal) {
        var comment = commentRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Comment with id: " + id + " not found"));
        if (comment.isAuthorized(principal)){
            commentRepository.deleteById(id);
        }
        else throw new UnauthorizedAccessException("You are not allowed to modify this resource");
    }

    @Override
    public void updateComment(AddOrUpdateCommentDto commentDto, String commentId, Principal principal) {
        var comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment with id: " + commentId + " not found"));
        if (comment.isAuthorized(principal)){
            comment.saveEdit();
            comment.setContent(commentDto.getContent());
            commentRepository.save(comment);
        }
        else throw new UnauthorizedAccessException("You are not allowed to modify this resource");
    }

    @Override
    public ActivityHistoryResponse getCommentHistory(String id) {
        var comment = commentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Comment with id : " + id + " + not found"));
        return new ActivityHistoryResponse(comment.getEditHistory());
    }


}
