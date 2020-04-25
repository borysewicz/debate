package com.example.Debate.dto.response;

import com.example.Debate.model.Vote;
import lombok.*;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class CommentResponse {

    String _id;
    String content;
    String parentActivityId;
    String author;
    Long creationDate;
    Long lastEditTime;
    int upvotes;
    int downvotes;
    @With
    Vote userVote;

}
