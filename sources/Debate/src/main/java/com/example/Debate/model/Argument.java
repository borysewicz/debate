package com.example.Debate.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
public class Argument extends Post{

    private String title;
    private Attitude attitude;
    private String debateId;

    public Argument() {
        super();
    }

    @PersistenceConstructor
    public Argument(long creationDate){
        super(creationDate);
    }

    public Argument(String title, Attitude attitude, String debateId, String content, String author){
        super();
        this.title = title;
        this.attitude = attitude;
        this.debateId = debateId;
        this.content = content;
        this.author = author;
    }

    @Override
    protected void putOldContent(long editTime) {
        this.editHistory.put(editTime,
                " Title: " + this.title +
                " Content: " + this.content
        );
    }

}
