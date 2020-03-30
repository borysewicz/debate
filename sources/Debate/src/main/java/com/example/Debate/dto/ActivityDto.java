package com.example.Debate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ActivityDto {
    private Long creationDate;
    private String content;
}
