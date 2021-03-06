package com.example.Debate.controller;

import com.example.Debate.dto.request.AddOrUpdateCommentDto;
import com.example.Debate.dto.request.RatingRequest;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.CommentResponse;
import com.example.Debate.dto.response.RatingResponse;
import com.example.Debate.jwt.UserPrincipal;
import com.example.Debate.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
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

    @GetMapping("/edits/{id}")
    public ResponseEntity<ActivityHistoryResponse> getCommentHistory(@PathVariable(name="id") String commentId){
        return ResponseEntity.ok(commentService.getCommentHistory(commentId));
    }

    @PostMapping("/addOnArgument")
    public ResponseEntity<CommentResponse> addCommentOnArgument(@RequestBody AddOrUpdateCommentDto commentDto, Principal principal) {
        var addedComment = this.commentService.addCommentOnArgument(commentDto, principal);
        return ResponseEntity.created(URI.create("api/comment/" + addedComment.get_id())).body(addedComment);
    }

    @PostMapping("/addOnDebate")
    public ResponseEntity<CommentResponse> addCommentOnDebate(@RequestBody AddOrUpdateCommentDto commentDto, Principal principal) {
        var addedComment = this.commentService.addCommentOnDebate(commentDto, principal);
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

    @PatchMapping("/rate/{id}")
    public ResponseEntity<RatingResponse> rateComment(@PathVariable(name="id") String commentId,
                                                      @RequestBody RatingRequest ratingRequest,
                                                      Principal principal){
        var response = this.commentService.rateComment(commentId, ratingRequest, principal);
        return ResponseEntity.ok(response);
    }

}
