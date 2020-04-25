package com.example.Debate.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Comment extends Post{
    public Comment() {
        super();
    }

    private String parentActivityId; //TODO check if field can be final

    @Override
    protected void putOldContent(long editTime) {
        this.editHistory.put(editTime, content);
    }

    @PersistenceConstructor
    public Comment(long creationDate){
        super(creationDate);
    }
}
