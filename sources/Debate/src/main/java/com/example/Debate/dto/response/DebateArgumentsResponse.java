package com.example.Debate.dto.response;

import lombok.Value;

import java.util.List;

@Value
public class DebateArgumentsResponse {
    List<ArgumentResponse> positive;
    List<ArgumentResponse> negative;
}
