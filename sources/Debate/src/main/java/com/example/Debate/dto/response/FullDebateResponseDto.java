package com.example.Debate.dto.response;

import lombok.NoArgsConstructor;
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
    Long lastEditionTime;
    int argumentCount;
    int commentCount;
    int voteCount;
    int participantCount;
    int viewCount;
    String author;
}
