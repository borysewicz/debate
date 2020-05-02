package com.example.Debate.controller;

import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService){this.searchService = searchService;}

    @GetMapping("/byName")
    public ResponseEntity<List<FullDebateResponseDto>> getDebatesContainingName(@RequestParam String reqName)
    {
        List<FullDebateResponseDto> debateDtoList = searchService.getDebatesDebatesContainingName(reqName);
        return ResponseEntity.status(HttpStatus.OK).body(debateDtoList);
    }

    @GetMapping("/byTags")
    public ResponseEntity<List<FullDebateResponseDto>> getDebatesWithTags(@RequestParam String Tags)
    {
        String[] tmp = Tags.split(";");
        List<FullDebateResponseDto> debateDtoList = searchService.getDebatesDebatesWithTags(
                Arrays.stream(tmp)
                        .map(tag -> tag.toLowerCase())
                        .collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(debateDtoList);
    }

}
