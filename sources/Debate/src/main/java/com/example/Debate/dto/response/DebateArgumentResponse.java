package com.example.Debate.dto.response;

import lombok.Value;

import java.util.List;

@Value
public class DebateArgumentResponse {
    List<ArgumentResponse> positive;
    List<ArgumentResponse> negative;
}
