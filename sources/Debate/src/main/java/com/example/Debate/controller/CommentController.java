package com.example.Debate.controller;

import com.example.Debate.dto.ArgumentDto;
import com.example.Debate.dto.CommentDto;
import com.example.Debate.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "id") String id){
        CommentDto commentDto;
        if((commentDto = commentService.getCommentById(id)) != null){
            return ResponseEntity.status(HttpStatus.OK).body(commentDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public HttpStatus addComment(@RequestBody CommentDto commentDto){
        if(commentService.addComment(commentDto))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }
}
