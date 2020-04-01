package com.example.Debate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ActivityDto {
    protected String _id;
    protected Long creationDate;
    protected String content;
}
