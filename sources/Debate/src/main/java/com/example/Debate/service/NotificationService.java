package com.example.Debate.service;

import com.example.Debate.dto.response.NotificationDto;

public interface NotificationService {

    NotificationDto getNotificationById(String id);
    boolean addNotification(NotificationDto notificationDto);
    
}
