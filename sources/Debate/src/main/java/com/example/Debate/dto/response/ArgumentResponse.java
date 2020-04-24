package com.example.Debate.dto.response;

import lombok.Value;

@Value
public class ArgumentResponse {

    String _id;
    String title;
    String content;
    String userVote;
    String attitude;
    String author;
    int upVotes;
    int downVotes;
    long lastEditDate;
    String debateId;

}
