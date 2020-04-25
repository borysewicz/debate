package com.example.Debate.dto.request;

import com.example.Debate.model.Vote;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class RatingRequest {
    Vote vote;
}
