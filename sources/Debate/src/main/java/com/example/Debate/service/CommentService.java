package com.example.Debate.service;

import com.example.Debate.dto.request.AddOrUpdateCommentDto;
import com.example.Debate.dto.request.RatingRequest;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.CommentResponse;
import com.example.Debate.dto.response.RatingResponse;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


public interface CommentService {

    CommentResponse getCommentById(String id, Optional<String> userLogin);

    CommentResponse addCommentOnArgument(AddOrUpdateCommentDto commentDto, Principal principal);

    CommentResponse addCommentOnDebate(AddOrUpdateCommentDto commentDto, Principal principal);

    void deleteComment(String id, Principal principal);

    void updateComment(AddOrUpdateCommentDto commentDto, String commentId, Principal principal);

    ActivityHistoryResponse getCommentHistory(String id);

    RatingResponse rateComment(String commentId, RatingRequest ratingRequest, Principal principal);
}
