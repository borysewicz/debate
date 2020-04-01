package com.example.Debate.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
@Getter
@Setter
public class User {

    @Id
    private String ID;
    private String login;
    private String password;
    private String eMail;
    private Role role;
}
