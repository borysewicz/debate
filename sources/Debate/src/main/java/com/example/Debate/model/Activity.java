package com.example.Debate.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public abstract class Activity {
    @Id
    protected String _id;
    protected Long creationDate;
    protected String content;
}
