package com.example.Debate.controller;

import com.example.Debate.dto.request.AddOrUpdateCommentDto;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.CommentResponse;
import com.example.Debate.jwt.UserPrincipal;
import com.example.Debate.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
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
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable(value = "id") String id, Principal principal){
        var response = commentService.getCommentById(id, UserPrincipal.getUserId(principal));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activity/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsForActivity(@PathVariable(name="id") String activityId,
                                                                        Principal principal){
        var comments = commentService.getCommentsForActivity(activityId, UserPrincipal.getUserId(principal));
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/edits/{id}")
    public ResponseEntity<ActivityHistoryResponse> getCommentHistory(@PathVariable(name="id") String commentId){
        return ResponseEntity.ok(commentService.getCommentHistory(commentId));
    }

    @PostMapping()
    public ResponseEntity<CommentResponse> addComment(@RequestBody AddOrUpdateCommentDto commentDto, Principal principal) {
        var addedComment = this.commentService.addComment(commentDto, principal);
        return ResponseEntity.created(URI.create("api/comment/" + addedComment.get_id())).body(addedComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editComment(@PathVariable(name="id") String commentId,
                                            @RequestBody AddOrUpdateCommentDto commentDto,
                                            Principal principal){
        this.commentService.updateComment(commentDto, commentId,  principal);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(name="id") String commentId,
                                              Principal principal){
        this.commentService.deleteComment(commentId, principal);
        return ResponseEntity.noContent().build();
    }

}
