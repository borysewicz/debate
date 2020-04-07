package com.example.Debate.dto.response;

import com.example.Debate.model.Activity;
import com.example.Debate.model.Punishment;
import com.example.Debate.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Value
public class NotificationDto {

        String _id;
        String message;
        boolean isRead;
        Punishment punishment;
        Activity activity;
        User recipient;
        User sender;

}
