package com.example.Debate.controller;

import com.example.Debate.dto.response.DebateDto;
import com.example.Debate.service.DebateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<DebateDto> getDebateById(@PathVariable(value = "id") String id){
        DebateDto debateDto = debateService.getDebateById(id);
        return ResponseEntity.status(HttpStatus.OK).body(debateDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DebateDto>> getAllDebates(){
        List<DebateDto> debateDtoList = debateService.getAllDebates();
        return ResponseEntity.status(HttpStatus.OK).body(debateDtoList);
    }

    @PostMapping("/add")
    public HttpStatus addDebate(@RequestBody DebateDto debateDto){
        return debateService.addDebate(debateDto) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }
}
