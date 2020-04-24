package com.example.Debate.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class Activity {
    @Id
    protected String _id;
    protected final Long creationDate;
    protected String author;
    protected String content;
    protected long lastEditionTime;
    protected Map<Long, String> editHistory;


    protected Activity(){
        this.creationDate = System.currentTimeMillis();
        this.lastEditionTime = this.creationDate;
        this.editHistory = new HashMap<>();
    }

    public Activity(long creationDate) {
        this.creationDate = creationDate;
    }

    public void saveEdit(){
        var currentTime = System.currentTimeMillis();
        this.putOldContent(currentTime);
        this.lastEditionTime = currentTime;
    }

    protected abstract void putOldContent(long editTime);

}
