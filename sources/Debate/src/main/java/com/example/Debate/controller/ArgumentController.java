package com.example.Debate.controller;

import com.example.Debate.dto.response.ArgumentResponse;
import com.example.Debate.service.ArgumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/argument")
public class ArgumentController {
    private ArgumentService argumentService;

    public ArgumentController(ArgumentService argumentService) {
        this.argumentService = argumentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArgumentResponse> getArgumentById(@PathVariable(value = "id") String id){
        ArgumentResponse argumentDto;
        if((argumentDto = argumentService.getArgumentById(id)) != null){
            return ResponseEntity.status(HttpStatus.OK).body(argumentDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArgumentResponse>> getAllArguments(){
        List<ArgumentResponse> argumentDtoList = argumentService.getAllArguments();
        return argumentDtoList.size() != 0 ? ResponseEntity.status(HttpStatus.OK).body(argumentDtoList) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(argumentDtoList);
    }

    @PostMapping("/add")
    public HttpStatus addArgument(@RequestBody ArgumentResponse argumentDto){
        return argumentService.addArgument(argumentDto) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }
}
