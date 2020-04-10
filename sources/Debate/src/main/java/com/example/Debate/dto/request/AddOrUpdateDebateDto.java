package com.example.Debate.dto.request;

import lombok.Value;
import lombok.experimental.NonFinal;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@NonFinal
public class AddOrUpdateDebateDto {

    @Size(min = 5, max = 50)
    String title;
    @Size(min = 5, max = 300)
    String description;
    @NotNull
    @Size(min = 3, max = 3)
    String[] mainTags;
    @Size(min = 3, max = 7)
    @NotNull
    String[] allTags;

}
