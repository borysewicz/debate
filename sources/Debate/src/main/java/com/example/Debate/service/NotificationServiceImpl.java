package com.example.Debate.service;

import com.example.Debate.dto.NotificationDto;
import com.example.Debate.model.Notification;
import com.example.Debate.repository.NotificationRepository;
import org.modelmapper.ModelMapper;

@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository notificationRepository;
    private ModelMapper modelMapper;

    public NotificationServiceImpl(NotificationRepository notificationRepository, ModelMapper modelMapper) {
        this.notificationRepository = notificationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationDto getNotificationById(String id) {
        Notification notification = notificationRepository.findById(id).get();
        return modelMapper.map(notification,NotificationDto.class);
    }

    public boolean addNotification(NotificationDto notificationDto)
    {
        Notification notification = modelMapper.map(notificationDto,Notification.class);
        notificationRepository.save(notification);
        return true;
    }
}
