package com.example.Debate.service;

import com.example.Debate.dto.request.AddOrUpdateCommentDto;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.CommentResponse;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


public interface CommentService {
    CommentResponse getCommentById(String id, Optional<String> userLogin);
    CommentResponse addComment(AddOrUpdateCommentDto commentDto, Principal principal);
    List<CommentResponse> getCommentsForActivity(String activityId, Optional<String> userLogin);
    void deleteComment(String id, Principal principal);
    void updateComment(AddOrUpdateCommentDto commentDto, String commentId, Principal principal);
    ActivityHistoryResponse getCommentHistory(String id);

}
