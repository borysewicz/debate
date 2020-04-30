package com.example.Debate.service;

import com.example.Debate.dto.response.NotificationDto;

public interface NotificationService {

    public NotificationDto getNotificationById(String id);
    public boolean addNotification(NotificationDto notificationDto);
    
}
