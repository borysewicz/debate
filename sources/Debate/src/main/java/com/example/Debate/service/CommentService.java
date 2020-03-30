package com.example.Debate.service;

import com.example.Debate.dto.CommentDto;

public interface CommentService {
    public CommentDto getCommentById(String id);
    public boolean addComment(CommentDto commentDto);
}
