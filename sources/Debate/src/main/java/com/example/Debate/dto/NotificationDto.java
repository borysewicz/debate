package com.example.Debate.dto;

import com.example.Debate.model.Activity;
import com.example.Debate.model.Punishment;
import com.example.Debate.model.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NotificationDto {

        String _id;
        String message;
        boolean isRead;
        Punishment punishment;
        Activity activity;
        User recipient;
        User sender;

}
