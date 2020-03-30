package com.example.Debate.controller;

import com.example.Debate.dto.DebateDto;
import com.example.Debate.service.DebateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if(debateDto != null){
            return ResponseEntity.status(HttpStatus.OK).body(debateDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public HttpStatus addDebate(@RequestBody DebateDto debateDto){
        if(debateService.addDebate(debateDto))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }
}
