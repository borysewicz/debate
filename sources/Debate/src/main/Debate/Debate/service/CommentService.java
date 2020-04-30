package com.example.Debate.service;

import com.example.Debate.dto.response.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto getCommentById(String id);
    public List<CommentDto> getAllComments();
    public boolean addComment(CommentDto commentDto);
}
