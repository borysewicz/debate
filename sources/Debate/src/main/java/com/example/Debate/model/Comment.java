package com.example.Debate.model;

public class Comment extends Post{
    public Comment() {
        super();
    }

    @Override
    protected void putOldContent(long editTime) {
        this.editHistory.put(editTime, content);
    }
}
