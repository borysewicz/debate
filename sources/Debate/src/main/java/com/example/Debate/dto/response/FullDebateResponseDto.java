package com.example.Debate.dto.response;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
@NoArgsConstructor(force = true)
public class FullDebateResponseDto {
    String _id;
    String content;
    String title;
    String[] mainTags;
    String[] allTags;
    Long creationDate;
    Long lastEditTime;
    int argumentCount;
    int commentCount;
    int voteCount;
    int participantCount;
    String author;

    public FullDebateResponseDto(String _id, String content, String title, String[] mainTags, String[] allTags, Long creationDate, Long lastEditTime, int argumentCount, int commentCount, int voteCount, int participantCount, String author) {
        this._id = _id;
        this.content = content;
        this.title = title;
        this.mainTags = mainTags;
        this.allTags = allTags;
        this.creationDate = creationDate;
        this.lastEditTime = lastEditTime;
        this.argumentCount = argumentCount;
        this.commentCount = commentCount;
        this.voteCount = voteCount;
        this.participantCount = participantCount;
        this.author = author;
    }
}
