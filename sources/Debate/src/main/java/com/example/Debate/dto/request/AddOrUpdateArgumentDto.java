package com.example.Debate.dto.request;

import com.example.Debate.model.enums.Attitude;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class AddOrUpdateArgumentDto {

    @Size(min = 5, max = 50, message = "The title has to be between 5 and 50 characters long")
    String title;
    @Size(min = 5, max = 300, message = "The content has to be between 5 and 300 characters long")
    String content;
    @NotNull(message = "Attitude must be provided")
    Attitude attitude;
    @NotEmpty(message = "Debate id must be provided")
    String debateId;

}
