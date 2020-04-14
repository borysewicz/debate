package com.example.Debate.dto.request;

import lombok.Value;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@NonFinal
public class AddOrUpdateDebateDto {

    @Size(min = 5, max = 50, message = "The title has to be between 5 and 50 characters long")
    String title;
    @Size(min = 5, max = 600, message = "The description has to be between 5 and 600 characters long")
    String description;
    @NotNull
    @Size(min = 3, max = 3, message = "Exactly 3 main tags are required")
    String[] mainTags;
    @Size(min = 3, max = 7, message = "Minimum of 3 and maximum of 7 tags is permitted")
    @NotNull
    String[] allTags;

}
