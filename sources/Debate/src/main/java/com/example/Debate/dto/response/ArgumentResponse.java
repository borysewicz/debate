package com.example.Debate.dto.response;

import com.example.Debate.model.enums.Vote;
import lombok.*;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class ArgumentResponse {

    String _id;
    String title;
    String content;
    @With
    Vote userVote;
    String attitude;
    String author;
    int upVotes;
    int downVotes;
    long lastEditTime;
    String debateId;

}
