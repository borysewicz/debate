package com.example.Debate.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Report {

    @Id
    String _id;
    String violation;
    long submitDate;
    long acceptDate;
    @DBRef
    Punishment punishment;
    @DBRef
    User submitter;
    @DBRef
    User reviewer;
    @DBRef
    Activity activity;

}
