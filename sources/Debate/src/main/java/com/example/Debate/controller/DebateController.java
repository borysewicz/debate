package com.example.Debate.controller;

import com.example.Debate.common.api.SortingType;
import com.example.Debate.dto.request.AddOrUpdateDebateDto;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.ArgumentResponse;
import com.example.Debate.dto.response.CommentResponse;
import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.jwt.UserPrincipal;
import com.example.Debate.service.DebateService;
import org.bson.types.Binary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/debate")
public class DebateController {
    private DebateService debateService;

    public DebateController(DebateService debateService) {
        this.debateService = debateService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullDebateResponseDto> getDebateById(@PathVariable(value = "id") String id) {
        var debateDto = debateService.getDebateById(id);
        return ResponseEntity.status(HttpStatus.OK).body(debateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebate(@PathVariable String id, Principal principal) {
        debateService.delete(id, principal);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data", "multipart/mixed"})
    public ResponseEntity<Void> updateDebate(@RequestPart(value = "img", required = false) MultipartFile debateCover,
                                             @RequestPart(value = "debate") @Valid AddOrUpdateDebateDto debateDto,
                                             @PathVariable String id,
                                             Principal principal) {
        debateService.update(id, debateDto, debateCover, principal);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/cover/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<ByteArrayResource> getCoverImage(@PathVariable String id) {
        Binary image = debateService.getDebateCover(id);
        return ResponseEntity.ok()
                .body(new ByteArrayResource(image.getData()));
    }

    @GetMapping
    public ResponseEntity<List<FullDebateResponseDto>> getDebates(@RequestParam(defaultValue = "New") SortingType sort,
                                                                  @RequestParam(defaultValue = "12") int limit,
                                                                  @RequestParam(defaultValue = "0") int page) {
        List<FullDebateResponseDto> debateDtoList = debateService.getDebates(sort, limit, page);
        return ResponseEntity.status(HttpStatus.OK).body(debateDtoList);
    }

    @PostMapping(value = "/add", consumes = {"multipart/form-data", "multipart/mixed"})
    public ResponseEntity<FullDebateResponseDto> addDebate(@RequestPart(value = "img", required = false) MultipartFile debateCover,
                                                           @RequestPart(value = "debate") @Valid AddOrUpdateDebateDto debateDto,
                                                           Principal principal) {
        var newDebate = debateService.addDebate(debateDto, debateCover, principal);
        return ResponseEntity.created(URI.create("/api/debate/" + newDebate.get_id())).body(newDebate);
    }

    @GetMapping("/edits/{id}")
    public ResponseEntity<ActivityHistoryResponse> getDebateHistory(@PathVariable String id) {
        var history = this.debateService.getDebateHistory(id);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentResponse>> getCommentsForDebate(@PathVariable(name="id") String debateId,
                                                                        Principal principal){
        var comments = debateService.getCommentsForDebate(debateId, UserPrincipal.getUserId(principal));
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/arguments/{id}")
    public ResponseEntity<List<ArgumentResponse>> getArgumentsForDebate(@PathVariable(name="id") String debateId,
                                                                         Principal principal){
        var comments = debateService.getArgumentsForDebate(debateId, UserPrincipal.getUserId(principal));
        return ResponseEntity.ok(comments);
    }
}
