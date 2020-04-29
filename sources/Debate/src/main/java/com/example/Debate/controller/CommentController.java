package com.example.Debate.controller;

import com.example.Debate.dto.response.CommentDto;
import com.example.Debate.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<CommentDto>> getAllComments(){
        List<CommentDto> commentDtoList = commentService.getAllComments();
        return commentDtoList.size() != 0 ? ResponseEntity.status(HttpStatus.OK).body(commentDtoList) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(commentDtoList);
    }

    @PostMapping("/add")
    public HttpStatus addComment(@RequestBody CommentDto commentDto){
        return commentService.addComment(commentDto) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }
}
