package com.example.Debate.controller;

import com.example.Debate.dto.NotificationDto;
import com.example.Debate.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/notification")
public class NotificationController {
    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable(value = "id") String id){
        NotificationDto notificationDto;
        if((notificationDto = notificationService.getNotificationById(id)) != null){
            return ResponseEntity.status(HttpStatus.OK).body(notificationDto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public HttpStatus addNotification(@RequestBody NotificationDto notificationDto){
        if(notificationService.addNotification(notificationDto))
            return HttpStatus.OK;
        return HttpStatus.NOT_FOUND;
    }
}
