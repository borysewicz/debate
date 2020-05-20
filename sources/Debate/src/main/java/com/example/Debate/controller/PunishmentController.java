package com.example.Debate.controller;

import com.example.Debate.dto.response.PunishmentDto;
import com.example.Debate.service.PunishmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/punishment")
public class PunishmentController {

    private PunishmentService punishmentService;

    public PunishmentController(PunishmentService punishmentService) {
        this.punishmentService = punishmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PunishmentDto> getPunishmentById(@PathVariable(value = "id") String id){
        PunishmentDto punishmentDto;
        if((punishmentDto = punishmentService.getPunishmentById(id)) != null){
            return ResponseEntity.status(HttpStatus.OK).body(punishmentDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public HttpStatus addPunishment(@RequestBody PunishmentDto punishmentDto){
        if(punishmentService.addPunishment(punishmentDto))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }

}
