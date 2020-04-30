package com.example.Debate.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Argument extends Post{
    public Argument() {
        super();
    }
}
