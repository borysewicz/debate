package com.example.Debate.dto.request;

import lombok.Value;

@Value
public class AddOrUpdateCommentDto {

    String content;
    String parentActivityId;

}
