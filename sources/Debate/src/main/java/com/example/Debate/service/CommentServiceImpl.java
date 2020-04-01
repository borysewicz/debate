package com.example.Debate.service;

import com.example.Debate.dto.CommentDto;
import com.example.Debate.dto.DebateDto;
import com.example.Debate.model.Comment;
import com.example.Debate.model.Debate;
import com.example.Debate.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto getCommentById(String id) {
        Comment comment = commentRepository.findById(id).get();
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> commentList = commentRepository.findAll();
        List<CommentDto> commentDtoList = new ArrayList<>();
        if(commentList.size() != 0){
            for(Comment comment : commentList){
                commentDtoList.add(modelMapper.map(comment,CommentDto.class));
            }
        }
        return commentDtoList;
    }

    @Override
    public boolean addComment(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto,Comment.class);
        commentRepository.save(comment);
        return true;
    }
}
