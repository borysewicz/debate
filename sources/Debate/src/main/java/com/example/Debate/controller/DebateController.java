package com.example.Debate.controller;

import com.example.Debate.common.api.SortingType;
import com.example.Debate.dto.request.AddOrUpdateDebateDto;
import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.service.DebateService;
import org.bson.types.Binary;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<Void> deleteDebate(@PathVariable String id){
        debateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data", "multipart/mixed"})
    public ResponseEntity<Void> updateDebate(@RequestPart(value = "img", required = false) MultipartFile debateCover,
                                                           @RequestPart(value = "debate") @Valid AddOrUpdateDebateDto debateDto,
                                                           @PathVariable String id){
        debateService.update(id, debateDto, debateCover);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/cover/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<ByteArrayResource> getCoverImage(@PathVariable String id){
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
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<FullDebateResponseDto> addDebate(@RequestPart(value = "img", required = false) MultipartFile debateCover,
                                                           @RequestPart(value = "debate") @Valid AddOrUpdateDebateDto debateDto) {
        var newDebate = debateService.addDebate(debateDto, debateCover);
        return ResponseEntity.created(URI.create("/api/debate/" + newDebate.get_id())).body(newDebate);
    }

}
