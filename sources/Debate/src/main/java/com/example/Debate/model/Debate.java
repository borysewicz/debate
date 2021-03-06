package com.example.Debate.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@Document
@Getter
@Setter
public class Debate extends Activity{

    String[] mainTags;
    String[] allTags;
    String title;
    Binary image;
    Set<String> arguments;

    public Debate(){
        super();
        this.arguments = new HashSet<>();
    }

    @PersistenceConstructor
    public Debate(long creationDate){
        super(creationDate);

    }

    @Override
    protected void putOldContent(long editTime) {
        this.editHistory.put(editTime,
                "Title: " + this.title +
                    " Content: " + this.content +
                    " allTags: " + Arrays.toString(this.allTags)
        );
    }
}
