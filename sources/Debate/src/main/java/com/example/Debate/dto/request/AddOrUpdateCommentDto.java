package com.example.Debate.dto.request;

import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Value
public class AddOrUpdateCommentDto {

    @Size(min = 2, max = 300, message = "The comment has to be between 2 and 300 characters long")
    String content;
    @NotEmpty(message = "Parent activity id must be provided")
    String parentActivityId;

}
