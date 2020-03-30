package com.example.Debate.controller;

import com.example.Debate.dto.ArgumentDto;
import com.example.Debate.service.ArgumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/argument")
public class ArgumentController {
    private ArgumentService argumentService;

    public ArgumentController(ArgumentService argumentService) {
        this.argumentService = argumentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArgumentDto> getArgumentById(@PathVariable(value = "id") String id){
        ArgumentDto argumentDto;
        if((argumentDto = argumentService.getArgumentById(id)) != null){
            return ResponseEntity.status(HttpStatus.OK).body(argumentDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public HttpStatus addArgument(@RequestBody ArgumentDto argumentDto){
        if(argumentService.addArgument(argumentDto))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }
}
