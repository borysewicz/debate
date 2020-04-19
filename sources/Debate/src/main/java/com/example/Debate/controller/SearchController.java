package com.example.Debate.controller;

import com.example.Debate.dto.response.FullDebateResponseDto;
import com.example.Debate.service.SearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/search")
public class SearchController {

    private SearchService searchService;

    public SearchController(SearchService searchService){this.searchService = searchService;}

    @GetMapping("/byName")
    public ResponseEntity<List<FullDebateResponseDto>> getDebatesContainingName(@RequestParam String reqName)
    {
//        System.out.println(reqName);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<FullDebateResponseDto>());
        List<FullDebateResponseDto> debateDtoList = searchService.getDebatesDebatesContainingName(reqName);
        return debateDtoList.size() != 0 ? ResponseEntity.status(HttpStatus.OK).body(debateDtoList) :
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(debateDtoList);
    }

    @GetMapping("/byTags")
    public ResponseEntity<List<FullDebateResponseDto>> getDebatesWithTags(@RequestParam List<String> reqTags)
    {
//        for(int i = 0;i<reqTags.size();i++)
//        {
//            System.out.println(reqTags.get(i));
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<FullDebateResponseDto>());
        List<FullDebateResponseDto> debateDtoList = searchService.getDebatesDebatesWithName(reqTags);
        return debateDtoList.size() != 0 ? ResponseEntity.status(HttpStatus.OK).body(debateDtoList) :
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(debateDtoList);
    }

}
