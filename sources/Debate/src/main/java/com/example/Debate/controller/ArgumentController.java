package com.example.Debate.controller;

import com.example.Debate.dto.request.AddOrUpdateArgumentDto;
import com.example.Debate.dto.response.ActivityHistoryResponse;
import com.example.Debate.dto.response.ArgumentResponse;
import com.example.Debate.service.ArgumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/argument")
public class ArgumentController {
    private ArgumentService argumentService;

    public ArgumentController(ArgumentService argumentService) {
        this.argumentService = argumentService;
    }

    @GetMapping
    public ResponseEntity<List<ArgumentResponse>> getArgumentsForDebate(@RequestParam(name="debate") String debateId,
                                                                        @RequestParam(defaultValue = "10") int limit,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        Principal principal){
        var args = argumentService.getArgumentsForDebate(debateId, limit, page, getUserId(principal));
        return ResponseEntity.ok(args);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArgumentResponse> getArgumentById(@PathVariable(name="id") String argId, Principal principal){
        var arg = argumentService.getArgumentById(argId, getUserId(principal));
        return ResponseEntity.ok(arg);
    }

    @PostMapping("/add")
    public ResponseEntity<ArgumentResponse> addArgument(@RequestBody AddOrUpdateArgumentDto argumentDto,
                                                        Principal principal){
        var newArgument = argumentService.addArgument(argumentDto, principal.getName());
        return ResponseEntity.created(URI.create("api/argument/" + newArgument.get_id())).body(newArgument);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArgument(@PathVariable(name= "id") String argId, Principal principal){
        argumentService.deleteArgument(argId, principal);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateArgument(@PathVariable String id,
                                               @RequestBody AddOrUpdateArgumentDto argumentDto,
                                               Principal principal){
        argumentService.updateArgument(id, argumentDto, principal);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/edits/{id}")
    public ResponseEntity<ActivityHistoryResponse> getDebateHistory(@PathVariable String id){
        var history = this.argumentService.getArgumentHistory(id);
        return ResponseEntity.ok(history);
    }

    private Optional<String> getUserId(Principal principal){
        if (principal == null){
            return Optional.empty();
        }else return Optional.of(principal.getName());
    }

}
