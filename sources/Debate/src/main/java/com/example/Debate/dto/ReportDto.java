package com.example.Debate.dto;

import com.example.Debate.model.Activity;
import com.example.Debate.model.Punishment;
import com.example.Debate.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
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
