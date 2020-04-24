package com.example.Debate.dto.request;

import com.example.Debate.model.Attitude;
import lombok.Value;

import javax.validation.constraints.Size;

@Value
public class AddOrUpdateArgumentDto {

    @Size(min = 5, max = 50, message = "The title has to be between 5 and 50 characters long")
    String title;
    @Size(min = 5, max = 300, message = "The content has to be between 5 and 300 characters long")
    String content;
    Attitude attitude;
    String debateId;

}
