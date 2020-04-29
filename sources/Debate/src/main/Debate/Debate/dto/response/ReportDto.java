package com.example.Debate.dto.response;

import com.example.Debate.model.Activity;
import com.example.Debate.model.Punishment;
import com.example.Debate.model.User;
import lombok.Value;

@Value
public class ReportDto {

    String _id;
    String violation;
    long submitDate;
    long acceptDate;
    Punishment punishment;
    User submitter;
    User reviewer;
    Activity activity;

}
