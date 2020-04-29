package com.example.Debate.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Notification {

    @Id
    String _id;
    String message;
    boolean isRead;
    @DBRef
    Punishment punishment;
    @DBRef
    Activity activity;
    @DBRef
    User recipient;
    @DBRef
    User sender;

}
