package com.example.Debate.dto.response;

import lombok.Value;

import java.util.Map;

@Value
public class ActivityHistoryResponse {

    Map<Long, String> editHistory;

}
